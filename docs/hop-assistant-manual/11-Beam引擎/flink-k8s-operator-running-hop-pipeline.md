# 使用 Flink Kubernetes Operator 运行 Hop pipeline

## 前提条件

请查看 [Beam 入门](pipeline/beam/getting-started-with-beam.md)指南，以了解 Hop 中 Beam 集成的基本概念。
以下是我们在下面示例中使用 Flink Kubernetes Operator 所需的文件：

- Hop fat jar：为您要使用的 Hop 版本生成一次（使用 Hop GUI 的 `Tools` 菜单或使用 `hop-conf.sh -fj`）。
- Hop metadata 导出：包含您项目 metadata 的 JSON 文件（使用 Hop GUI 的 `Tools` 菜单或使用 `hop-conf.sh -xm`）。
- Hop pipeline：包含 pipeline metadata 的 .hpl 文件（XML）。

*注意*：Hop 能够直接向 [s3://](vfs/aws-s3-vfs.md) 存储桶（或 [gs://](hop-gui/../vfs/google-cloud-storage-vfs.md) 或 [azure://](vfs/azure-blob-storage-vfs.md)）读/写文件。

## Flink Kubernetes Operator

[Flink](http://flink.apache.org) Operator 旨在让您轻松地在 Kubernetes 上启动新的 Flink 集群部署。要在您自己的 K8s 环境中安装它，您可以按照[快速入门](https://nightlies.apache.org/flink/flink-kubernetes-operator-docs-release-1.0/docs/try-flink-kubernetes-operator/quick-start/)指南操作。

## 示例设置

我们将在 Amazon Web Services 的 Elastic Kubernetes Service (EKS) 上运行我们的设置。
我们的 Qi Hop fat jar 以及 pipeline 和 metadata JSON 文件都存储在 S3 上的一个文件夹中。

在我们的设置中，目标是使用一个无界（永不停止）的 pipeline 生成示例数据，然后将该数据发送到 Kinesis 流：

![将测试数据发送到 Kinesis 流, width="100%"](../assets/images/beam/synthetic-data-to-kinesis.svg)

## Flink 部署

在下面的 Flink 部署文件中，我们将执行以下与标准设置不同的操作：

- 由于我们使用 Qi Hop >= 2.0.0，因此运行 Java 11
- 由于我们使用 Qi Hop >= 2.10.0，因此运行 Java 17
- 由于我们使用 Qi Hop >= 2.18.0，因此运行 Java 21
- 创建一个名为 `hop-resources` 的临时卷，由所有镜像挂载到文件夹 `/hop` 中
- 在任何 Flink 容器启动之前，我们运行一个 initContainer 将我们的 Hop 文件（fat jar、pipeline、metadata）复制到 `hop-resources` 卷。容器 [agiledigital/s3-artifact-fetcher](https://hub.docker.com/r/agiledigital/s3-artifact-fetcher) 用于将 S3 文件夹与节点/容器共享的临时卷同步。
- 我们指定 Hop 的主 Beam 类，并将 pipeline 和 metadata JSON 文件名（现在可以在容器本地找到）以及 Flink Hop pipeline 运行配置的名称作为参数传入。

请考虑以下名为 `flink-deployment.yml` 的文件：

```
apiVersion: flink.apache.org/v1beta1
kind: FlinkDeployment
metadata:
  namespace: default
  name: hop-flink
spec:
  image: f[auto](1.15-java11
  flinkVersion: v1_15
  flinkConfiguration:
    taskmanager.numberOfTaskSlots: "8"
    taskmanager.memory.jvm-metaspace.size: "512m"
  serviceAccount: flink
  podTemplate:
    apiVersion: v1
    kind: Pod
    metadata:
      name: flink-pod-template
    spec:
      serviceAccount: flink
      containers:
        #
        # Make an ephemeral volume available to the main flink container
        #
        - name: flink-main-container
          volumeMounts:
            - mountPath: /hop
              name: hop-resources
      initContainers:
        #
        # Copy a folder from s3:// to an ephemeral volume
        # Put a Hop fat jar in it as well as Hop metadata (JSON)
        # and the pipeline to run.
        #
        - name: fetcher
          image: agiledigital/s3-artifact-fetcher
          env:
            - name: SOURCE_URL
              value: "s3://<source-folder-of-hop-fat-jar-and-metadata>"
            - name: ARTIFACT_DIR
              value: "/hop/"
            - name: RUNNER_USER
              value: root
            - name: AWS_ACCESS_KEY_ID
              value: <your-aws-access-key>
            - name: AWS_SECRET_ACCESS_KEY
              value: "<your-aws-secret-key>"
            - name: AWS_DEFAULT_REGION
              value: <your-aws-region>
          volumeMounts:
            - mountPath: /hop
              name: hop-resources
      volumes:
        - name: hop-resources
          emptyDir: {}
  jobManager:
    replicas: 1
    resource:
      memory: "2g"
      cpu: 1
  taskManager:
    resource:
      memory: "2g"
      cpu: 2
  job:
    jarURI: local:///hop/<your-hop-fat-jar>
    parallelism: 4
    upgradeMode: stateless
    entryClass: org.apache.hop.beam.run.MainBeam
    args:
      - /hop/<your-hop-pipeline-hpl>
      - /hop/hop-metadata.json
      - Flink

```
提示：您可以在 args 列表中的运行配置名称后提供第 4 个参数：要使用的环境配置文件的名称。

请注意，`parallelism` 参数在 `spec.job` 部分是必填的。但是此参数会被忽略，因为我们的 pipeline 使用 Apache Beam 执行。Flink runner 有自己的并行度设置。您可以在 pipeline 运行配置（上面称为 `Flink`）中配置此设置。在此运行配置中，您可以指定 Flink master `)`。

## 执行

正如 Flink Operator 文档中所示，我们现在可以通过运行以下命令来创建此 Flink 部署。

`kubectl create -f flink-deployment.yml`

然后您可以看到各个 pod 经过其初始化阶段：

`kubectl get pods`

显然，首先会发生的是我们需要一个 Flink job manager。在启动之前，我们通过使用 `s3-artifact-fetcher` 从 S3 复制数据来初始化 `hop-resources` 卷。
这导致一个 `init` 状态：

```
hop-flink-85b8fc7d5f-fkxvq                  0/1     Init:0/1   0          2s
```
过一会儿，我们将启动 job manager 本身，最终得到以下 pod 状态：

```
hop-flink-85b8fc7d5f-fkxvq                  1/1     Running   0          45s
```
一旦 job manager 就绪，作业本身将启动，并且（在我们的情况下）也会启动一个 task manager：

```
$ kubectl get pods
NAME                                        READY   STATUS    RESTARTS   AGE
flink-kubernetes-operator-6976569cb-68c6g   1/1     Running   0          2d16h
hop-flink-85b8fc7d5f-fkxvq                  1/1     Running   0          95s
hop-flink-taskmanager-1-1                   1/1     Running   0          21s
```
## 监控和日志

要查看正在发生的情况，我们可以从 job manager 进行端口转发：

```bash
kubectl port-forward pod/hop-flink-85b8fc7d5f-fkxvq 8081
Forwarding from 127.0.0.1:8081 -> 8081
Forwarding from [::1]:8081 -> 8081
Handling connection for 8081
...
```

现在您可以浏览到 [localhost:8081](http://localhost:8081) 查看 Flink 作业。

要查看 job manager 的详细日志，您可以运行 `kubectl logs`。来自 Hop pipeline 执行的日志可以在 Flink job manager 日志中找到：

```bash
kubectl logs -f pod/hop-flink-85b8fc7d5f-fkxvq
...
Argument 1 : Pipeline filename (.hpl)   : /hop/<your-hop-pipeline>.hpl
Argument 2 : Metadata filename (.json)  : /hop/hop-metadata.json
Argument 3 : Pipeline run configuration : Flink
>>>>>> Initializing Hop...
...
>>>>>> Loading pipeline metadata
>>>>>> Building Apache Beam Pipeline...
>>>>>> Found Beam Input transform plugin class loader
>>>>>> Pipeline executing starting...
2022/06/27 13:37:27 - General - Created Apache Beam pipeline with name 'synthetic-data-to-kinesis'
2022/06/27 13:37:28 - General - Handled transform (ROW GENERATOR) : oo rows
2022/06/27 13:37:28 - General - Handled generic transform (TRANSFORM) : sysdate, gets data from 1 previous transform(s), targets=0, infos=0
2022/06/27 13:37:28 - General - Handled generic transform (TRANSFORM) : Random values, gets data from 1 previous transform(s), targets=0, infos=0
2022/06/27 13:37:28 - General - Handled generic transform (TRANSFORM) : JSON output, gets data from 1 previous transform(s), targets=0, infos=0
2022/06/27 13:37:28 - General - Handled transform (KINESIS OUTPUT) : Beam Kinesis Produce, gets data from JSON output
2022/06/27 13:37:28 - General - Executing this pipeline using the Beam Pipeline Engine with run configuration 'Flink'
...
```

## 终止

要关闭作业以及集群，我们只需删除 Flink 部署：

```bash
kubectl delete -f flink-deployment.yml
```
