/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.ui.hopgui.file.pipeline;

import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.pipeline.Pipeline;
import org.apache.hop.pipeline.engine.IEngineMetric;

/**
 * Utility for displaying pipeline metrics in the UI with appropriate units. Does not change metric
 * storage or keys; only used for column headers and labels.
 */
public final class PipelineMetricDisplayUtil {

  private static final Class<?> PKG = PipelineMetricDisplayUtil.class;

  private PipelineMetricDisplayUtil() {}

  /**
   * Returns the metric header for display in the metrics tab/grid. The underlying metric key (e.g.
   * for lookup) remains the raw header from {@link IEngineMetric#getHeader()}.
   *
   * @param metric the engine metric
   * @param withUnit when true, append unit in parentheses (e.g. "Input (rows)"); when false, header
   *     only (e.g. "Input")
   * @return display label
   */
  public static String getDisplayHeader(IEngineMetric metric, boolean withUnit) {
    if (metric == null) {
      return "";
    }
    String header = getI18nHeader(metric);
    if (!withUnit) {
      return header;
    }
    String unit = getI18nUnitForMetric(metric);
    if (unit == null || unit.isEmpty()) {
      return header;
    }
    return header + " (" + unit + ")";
  }

  /**
   * Returns the metric header with the correct unit for display (same as getDisplayHeader(metric,
   * true)).
   */
  public static String getDisplayHeaderWithUnit(IEngineMetric metric) {
    return getDisplayHeader(metric, true);
  }

  /**
   * Returns the internationalized header for a metric.
   *
   * @param metric the engine metric
   * @return internationalized header, or raw header if not found
   */
  private static String getI18nHeader(IEngineMetric metric) {
    String code = metric.getCode();
    if (code == null) {
      return metric.getHeader();
    }
    String key =
        switch (code) {
          case Pipeline.METRIC_NAME_INPUT -> "PipelineExecutionViewer.MetricsTab.Column.Input";
          case Pipeline.METRIC_NAME_READ -> "PipelineExecutionViewer.MetricsTab.Column.Read";
          case Pipeline.METRIC_NAME_WRITTEN -> "PipelineExecutionViewer.MetricsTab.Column.Written";
          case Pipeline.METRIC_NAME_OUTPUT -> "PipelineExecutionViewer.MetricsTab.Column.Output";
          case Pipeline.METRIC_NAME_UPDATED -> "PipelineExecutionViewer.MetricsTab.Column.Updated";
          case Pipeline.METRIC_NAME_REJECTED ->
              "PipelineExecutionViewer.MetricsTab.Column.Rejected";
          case Pipeline.METRIC_NAME_ERROR -> "PipelineExecutionViewer.MetricsTab.Column.Errors";
          case Pipeline.METRIC_NAME_BUFFER_IN ->
              "PipelineExecutionViewer.MetricsTab.Column.BuffersInput";
          case Pipeline.METRIC_NAME_BUFFER_OUT ->
              "PipelineExecutionViewer.MetricsTab.Column.BuffersOutput";
          case Pipeline.METRIC_NAME_INIT -> "PipelineExecutionViewer.MetricsTab.Column.Inits";
          case Pipeline.METRIC_NAME_FLUSH_BUFFER ->
              "PipelineExecutionViewer.MetricsTab.Column.Flushes";
          case Pipeline.METRIC_NAME_DATA_VOLUME ->
              "PipelineExecutionViewer.MetricsTab.Column.DataVolume";
          case Pipeline.METRIC_NAME_DATA_VOLUME_IN ->
              "PipelineExecutionViewer.MetricsTab.Column.DataVolumeIn";
          case Pipeline.METRIC_NAME_DATA_VOLUME_OUT ->
              "PipelineExecutionViewer.MetricsTab.Column.DataVolumeOut";
          default -> null;
        };
    if (key != null) {
      String i18nValue = BaseMessages.getString(PKG, key);
      if (i18nValue != null && !i18nValue.isEmpty()) {
        return i18nValue;
      }
    }
    return metric.getHeader();
  }

  /**
   * Returns the unit label for a metric (e.g. "rows", "runs") for use in column headers. Returns
   * null if the metric has no unit.
   */
  public static String getUnitForMetric(IEngineMetric metric) {
    String code = metric.getCode();
    if (code == null) {
      return null;
    }

    return switch (code) {
      case Pipeline.METRIC_NAME_INPUT,
              Pipeline.METRIC_NAME_READ,
              Pipeline.METRIC_NAME_WRITTEN,
              Pipeline.METRIC_NAME_OUTPUT,
              Pipeline.METRIC_NAME_UPDATED,
              Pipeline.METRIC_NAME_REJECTED,
              Pipeline.METRIC_NAME_ERROR,
              Pipeline.METRIC_NAME_BUFFER_IN,
              Pipeline.METRIC_NAME_BUFFER_OUT ->
          "rows";
      case Pipeline.METRIC_NAME_INIT -> "runs";
      case Pipeline.METRIC_NAME_FLUSH_BUFFER -> "flushes";
      case Pipeline.METRIC_NAME_DATA_VOLUME ->
          // Cell shows scaled unit (B/KB/MB/GB) via formatDataVolume()
          null;
      case Pipeline.METRIC_NAME_DATA_VOLUME_IN, Pipeline.METRIC_NAME_DATA_VOLUME_OUT ->
          // Cell shows scaled unit (B/KB/MB/GB) via formatDataVolume()
          null;
      default -> null;
    };
  }

  /**
   * Returns the internationalized unit label for a metric for use in column headers. Returns null
   * if the metric has no unit.
   */
  private static String getI18nUnitForMetric(IEngineMetric metric) {
    String unit = getUnitForMetric(metric);
    if (unit == null) {
      return null;
    }
    String key =
        switch (unit) {
          case "rows" -> "PipelineExecutionViewer.MetricsTab.Unit.Rows";
          case "runs" -> "PipelineExecutionViewer.MetricsTab.Unit.Runs";
          case "flushes" -> "PipelineExecutionViewer.MetricsTab.Unit.Flushes";
          default -> null;
        };
    if (key != null) {
      String i18nValue = BaseMessages.getString(PKG, key);
      if (i18nValue != null && !i18nValue.isEmpty()) {
        return i18nValue;
      }
    }
    return unit;
  }

  /**
   * Returns the short unit label for a metric for use in grid cell values (e.g. "r" for rows so the
   * header can keep "rows"). Returns null if the metric has no unit.
   */
  public static String getUnitForMetricCell(IEngineMetric metric) {
    String unit = getUnitForMetric(metric);
    if (unit == null) {
      return null;
    }
    if ("rows".equals(unit)) {
      return "r";
    }
    return unit;
  }

  private static final long KB = 1024L;
  private static final long MB = KB * 1024L;
  private static final long GB = MB * 1024L;

  /**
   * Formats a byte count for display in the metrics panel with scaled unit (B, KB, MB, GB) and
   * xx.xx notation for values >= 1 KB.
   *
   * @param bytes byte count (e.g. from data volume metric), or null if not tracked
   * @return formatted string like "0 B", "512 B", "1.50 KB", "123.45 MB", "2.00 GB"; empty string
   *     if null
   */
  public static String formatDataVolume(Long bytes) {
    if (bytes == null || bytes < 0) {
      return bytes == null ? "" : "0 B";
    }
    if (bytes < KB) {
      return bytes + " B";
    }
    if (bytes < MB) {
      return String.format("%.2f KB", bytes / (double) KB);
    }
    if (bytes < GB) {
      return String.format("%.2f MB", bytes / (double) MB);
    }
    return String.format("%.2f GB", bytes / (double) GB);
  }
}
