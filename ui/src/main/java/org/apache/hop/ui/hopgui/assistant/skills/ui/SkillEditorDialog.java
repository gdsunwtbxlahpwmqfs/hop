/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.ui.hopgui.assistant.skills.ui;

import java.util.regex.Pattern;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.dialog.BaseDialog;
import org.apache.hop.ui.core.gui.GuiResource;
import org.apache.hop.ui.core.gui.WindowProperty;
import org.apache.hop.ui.core.widget.editor.IContentEditorWidget;
import org.apache.hop.ui.hopgui.ContentEditorFacade;
import org.apache.hop.ui.hopgui.assistant.skills.Skill;
import org.apache.hop.ui.hopgui.assistant.skills.SkillManager;
import org.apache.hop.ui.pipeline.transform.BaseTransformDialog;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

/**
 * SWT dialog for creating or editing a single AI assistant skill.
 *
 * <p>The upper portion of the dialog shows a metadata form (name, description, category, trigger,
 * priority, version and enabled flag). The lower portion provides a markdown content editor on the
 * left and a live preview on the right, separated by a horizontal {@link SashForm}.
 *
 * <p>When editing an existing skill all fields are pre-populated and the name becomes read-only
 * (the name doubles as the on-disk directory key). When creating a new skill the fields start blank
 * with sensible defaults.
 */
public class SkillEditorDialog extends Dialog {

  /** Shared package message bundle. */
  private static final Class<?> PKG = SkillManager.class;

  /** Kebab-case name validation pattern. */
  private static final Pattern NAME_PATTERN = Pattern.compile("[a-z0-9]+(-[a-z0-9]+)*");

  private static final Parser MARKDOWN_PARSER = Parser.builder().build();
  private static final HtmlRenderer HTML_RENDERER = HtmlRenderer.builder().build();

  /** Selectable skill categories (stored verbatim on the skill). */
  private static final String[] CATEGORIES = {
    "general", "pipeline", "database", "workflow", "custom"
  };

  private final PropsUi props;

  private Shell shell;

  /** The skill being edited, or {@code null} when creating a new one. */
  private final Skill skill;

  /** Whether we are creating a brand-new skill. */
  private boolean isNew;

  /** The skill produced by a successful save, or {@code null} if cancelled. */
  private Skill result;

  // ── Metadata form widgets ──
  private Text wName;
  private Text wDescription;
  private Combo wCategory;
  private Combo wTrigger;
  private Spinner wPriority;
  private Text wVersion;
  private Button wEnabled;

  // ── Content editor widgets ──
  private IContentEditorWidget editorWidget;
  private Browser wPreview;
  private Label wlTokens;

  /**
   * Creates a skill editor dialog.
   *
   * @param parent the parent shell
   * @param skill the skill to edit, or {@code null} to create a new skill
   */
  public SkillEditorDialog(Shell parent, Skill skill) {
    super(parent, SWT.NONE);
    props = PropsUi.getInstance();
    this.skill = skill;
  }

  /**
   * Opens the dialog and blocks until the user closes it.
   *
   * @return the saved skill, or {@code null} if the dialog was cancelled
   */
  public Skill open() {
    Shell parent = getParent();

    shell = new Shell(parent, BaseDialog.getDefaultDialogStyle());
    PropsUi.setLook(shell);
    shell.setImage(GuiResource.getInstance().getImageHopUi());
    shell.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Title"));

    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = PropsUi.getFormMargin();
    formLayout.marginHeight = PropsUi.getFormMargin();
    shell.setLayout(formLayout);

    int margin = PropsUi.getMargin();

    // ── Bottom buttons ──
    Button wSave = new Button(shell, SWT.PUSH);
    wSave.setText(BaseMessages.getString(PKG, "SkillsManager.Button.Save"));
    wSave.addListener(SWT.Selection, e -> ok());
    Button wCancel = new Button(shell, SWT.PUSH);
    wCancel.setText(BaseMessages.getString(PKG, "SkillsManager.Button.Cancel"));
    wCancel.addListener(SWT.Selection, e -> cancel());
    BaseTransformDialog.positionBottomButtons(shell, new Button[] {wCancel, wSave}, margin, null);

    // ── Metadata section (top ~35%) ──
    Composite wMetadata = buildMetadataSection(shell, margin);
    FormData fdMetadata = new FormData();
    fdMetadata.left = new FormAttachment(0, 0);
    fdMetadata.top = new FormAttachment(0, margin);
    fdMetadata.right = new FormAttachment(100, 0);
    fdMetadata.bottom = new FormAttachment(35, -margin);
    wMetadata.setLayoutData(fdMetadata);

    // ── Content editor section (bottom ~65%) ──
    SashForm wSash = new SashForm(shell, SWT.HORIZONTAL | SWT.SMOOTH);
    PropsUi.setLook(wSash);
    FormData fdSash = new FormData();
    fdSash.left = new FormAttachment(0, 0);
    fdSash.top = new FormAttachment(wMetadata, margin);
    fdSash.right = new FormAttachment(100, 0);
    fdSash.bottom = new FormAttachment(wSave, -margin);
    wSash.setLayoutData(fdSash);

    buildEditorPane(wSash, margin);
    buildPreviewPane(wSash, margin);
    wSash.setWeights(new int[] {1, 1});

    // Populate the fields and refresh the preview once before opening.
    getData();

    // Window handling: close/escape cancel the dialog.
    shell.addListener(SWT.Close, e -> cancel());
    shell.addListener(
        SWT.Traverse,
        e -> {
          if (e.detail == SWT.TRAVERSE_ESCAPE) {
            e.doit = false;
            cancel();
          }
        });

    shell.setMinimumSize(600, 650);
    BaseTransformDialog.setSize(shell);
    shell.open();

    Display display = shell.getDisplay();
    try {
      while (!shell.isDisposed()) {
        if (!display.readAndDispatch()) {
          display.sleep();
        }
      }
    } catch (Error error) {
      // RAP throws UIThreadTerminatedError when the browser session ends.
      if (!error.getClass().getSimpleName().equals("UIThreadTerminatedError")) {
        throw error;
      }
    }

    return result;
  }

  /**
   * Builds the metadata form composite (without setting its layout data).
   *
   * @param parent the parent shell
   * @param margin the dialog margin
   * @return the populated metadata composite
   */
  private Composite buildMetadataSection(Composite parent, int margin) {
    Composite composite = new Composite(parent, SWT.NONE);
    PropsUi.setLook(composite);
    GridLayout layout = new GridLayout(4, false);
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    layout.horizontalSpacing = margin;
    layout.verticalSpacing = margin;
    composite.setLayout(layout);

    // ── Name ──
    Label wlName = new Label(composite, SWT.RIGHT);
    wlName.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Name"));
    PropsUi.setLook(wlName);
    wlName.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

    wName = new Text(composite, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    PropsUi.setLook(wName);
    GridData gdName = new GridData(SWT.FILL, SWT.CENTER, true, false);
    gdName.horizontalSpan = 3;
    wName.setLayoutData(gdName);

    // ── Description ──
    Label wlDescription = new Label(composite, SWT.RIGHT);
    wlDescription.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Description"));
    PropsUi.setLook(wlDescription);
    wlDescription.setLayoutData(new GridData(SWT.END, SWT.TOP, false, false));

    wDescription = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
    PropsUi.setLook(wDescription);
    GridData gdDescription = new GridData(SWT.FILL, SWT.FILL, true, true);
    gdDescription.horizontalSpan = 3;
    gdDescription.heightHint = 60;
    wDescription.setLayoutData(gdDescription);

    // ── Category + Trigger ──
    Label wlCategory = new Label(composite, SWT.RIGHT);
    wlCategory.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Category"));
    PropsUi.setLook(wlCategory);
    wlCategory.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

    wCategory = new Combo(composite, SWT.READ_ONLY);
    wCategory.setItems(CATEGORIES);
    PropsUi.setLook(wCategory);
    wCategory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

    Label wlTrigger = new Label(composite, SWT.RIGHT);
    wlTrigger.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Trigger"));
    PropsUi.setLook(wlTrigger);
    wlTrigger.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

    wTrigger = new Combo(composite, SWT.READ_ONLY);
    wTrigger.setItems(
        BaseMessages.getString(PKG, "SkillsManager.Editor.Trigger.Global"),
        BaseMessages.getString(PKG, "SkillsManager.Editor.Trigger.Manual"));
    PropsUi.setLook(wTrigger);
    wTrigger.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

    // ── Priority + Version ──
    Label wlPriority = new Label(composite, SWT.RIGHT);
    wlPriority.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Priority"));
    PropsUi.setLook(wlPriority);
    wlPriority.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

    wPriority = new Spinner(composite, SWT.BORDER);
    wPriority.setMinimum(0);
    wPriority.setMaximum(100);
    wPriority.setIncrement(1);
    PropsUi.setLook(wPriority);
    wPriority.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

    Label wlVersion = new Label(composite, SWT.RIGHT);
    wlVersion.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Version"));
    PropsUi.setLook(wlVersion);
    wlVersion.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

    wVersion = new Text(composite, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    PropsUi.setLook(wVersion);
    wVersion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

    // ── Enabled ──
    wEnabled = new Button(composite, SWT.CHECK);
    wEnabled.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Enabled"));
    PropsUi.setLook(wEnabled);
    wEnabled.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

    return composite;
  }

  /**
   * Builds the left pane of the sash: the markdown content editor with a header label and a live
   * token-count label.
   *
   * @param parent the sash form
   * @param margin the dialog margin
   */
  private void buildEditorPane(SashForm parent, int margin) {
    Composite composite = new Composite(parent, SWT.NONE);
    PropsUi.setLook(composite);
    FormLayout layout = new FormLayout();
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    composite.setLayout(layout);

    Label wlContent = new Label(composite, SWT.NONE);
    wlContent.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Content"));
    PropsUi.setLook(wlContent);
    FormData fdlContent = new FormData();
    fdlContent.left = new FormAttachment(0, 0);
    fdlContent.top = new FormAttachment(0, 0);
    wlContent.setLayoutData(fdlContent);

    wlTokens = new Label(composite, SWT.RIGHT);
    PropsUi.setLook(wlTokens);
    FormData fdlTokens = new FormData();
    fdlTokens.right = new FormAttachment(100, 0);
    fdlTokens.top = new FormAttachment(0, 0);
    wlTokens.setLayoutData(fdlTokens);

    editorWidget = ContentEditorFacade.createContentEditor(composite, "markdown");
    FormData fdEditor = new FormData();
    fdEditor.left = new FormAttachment(0, 0);
    fdEditor.top = new FormAttachment(wlContent, margin);
    fdEditor.right = new FormAttachment(100, 0);
    fdEditor.bottom = new FormAttachment(100, 0);
    editorWidget.getControl().setLayoutData(fdEditor);

    // Update preview and token count on every keystroke.
    editorWidget.addModifyListener(e -> updatePreview());
  }

  /**
   * Builds the right pane of the sash: a read-only preview of the rendered markdown.
   *
   * @param parent the sash form
   * @param margin the dialog margin
   */
  private void buildPreviewPane(SashForm parent, int margin) {
    Composite composite = new Composite(parent, SWT.NONE);
    PropsUi.setLook(composite);
    FormLayout layout = new FormLayout();
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    composite.setLayout(layout);

    Label wlPreview = new Label(composite, SWT.NONE);
    wlPreview.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Preview"));
    PropsUi.setLook(wlPreview);
    FormData fdlPreview = new FormData();
    fdlPreview.left = new FormAttachment(0, 0);
    fdlPreview.top = new FormAttachment(0, 0);
    wlPreview.setLayoutData(fdlPreview);

    wPreview = new Browser(composite, SWT.NONE);
    FormData fdPreview = new FormData();
    fdPreview.left = new FormAttachment(0, 0);
    fdPreview.top = new FormAttachment(wlPreview, margin);
    fdPreview.right = new FormAttachment(100, 0);
    fdPreview.bottom = new FormAttachment(100, 0);
    wPreview.setLayoutData(fdPreview);
  }

  /** Populates the form fields from the skill (or applies defaults for a new skill). */
  private void getData() {
    if (skill != null) {
      isNew = false;
      wName.setText(skill.getName() != null ? skill.getName() : "");
      // The name is the on-disk directory key and must not change for an existing skill.
      wName.setEditable(false);
      wDescription.setText(skill.getDescription() != null ? skill.getDescription() : "");
      selectComboItem(wCategory, skill.getCategory() != null ? skill.getCategory() : "custom");
      wTrigger.select(skill.getTrigger() == Skill.TriggerType.GLOBAL ? 0 : 1);
      wPriority.setSelection(skill.getPriority());
      wVersion.setText(skill.getVersion() != null ? skill.getVersion() : "1.0.0");
      wEnabled.setSelection(skill.isEnabled());
      editorWidget.setTextSuppressModify(skill.getContent() != null ? skill.getContent() : "");
    } else {
      isNew = true;
      wCategory.select(CATEGORIES.length - 1); // custom
      wTrigger.select(1); // Manual Trigger
      wPriority.setSelection(0);
      wVersion.setText("1.0.0");
      wEnabled.setSelection(true);
    }

    updatePreview();
    wName.setFocus();
    if (!isNew) {
      // Move the caret to the editor since the name is read-only.
      editorWidget.getControl().setFocus();
    } else {
      wName.setSelection(wName.getText().length());
    }
  }

  /** Refreshes the preview pane and token-count label from the current editor content. */
  private void updatePreview() {
    String content = editorWidget.getText();
    String html = renderMarkdown(content);
    wPreview.setText(html);
    int tokens = content.length() / 4;
    wlTokens.setText(BaseMessages.getString(PKG, "SkillsManager.Label.Tokens", tokens));
  }

  private String renderMarkdown(String markdown) {
    if (markdown == null || markdown.isBlank()) {
      return "<html><body><p style='color:#666'>No content</p></body></html>";
    }
    String bodyHtml = HTML_RENDERER.render(MARKDOWN_PARSER.parse(markdown));
    return "<html><head><style>body { font-family: sans-serif; padding: 8px; font-size: 14px; } pre { background: #f4f4f4; padding: 8px; border-radius: 4px; } code { background: #f4f4f4; padding: 2px 4px; border-radius: 2px; } blockquote { border-left: 4px solid #0E3A5A; margin: 0; padding-left: 8px; color: #666; }</style></head><body>"
        + bodyHtml
        + "</body></html>";
  }

  /**
   * Selects the combo entry matching the given value (case-insensitive), falling back to the first
   * item.
   *
   * @param combo the combo box
   * @param value the value to select
   */
  private void selectComboItem(Combo combo, String value) {
    if (value == null) {
      combo.select(0);
      return;
    }
    for (int i = 0; i < combo.getItemCount(); i++) {
      if (combo.getItem(i).equalsIgnoreCase(value)) {
        combo.select(i);
        return;
      }
    }
    combo.select(0);
  }

  /**
   * Validates the input and, on success, persists the skill through {@link SkillManager} and closes
   * the dialog.
   */
  private void ok() {
    String name = wName.getText().trim();
    if (name.isEmpty()) {
      showError(BaseMessages.getString(PKG, "SkillsManager.Editor.NameRequired"));
      return;
    }
    if (!NAME_PATTERN.matcher(name).matches()) {
      showError(BaseMessages.getString(PKG, "SkillsManager.Editor.NamePattern"));
      return;
    }

    String description = wDescription.getText().trim();
    if (description.isEmpty()) {
      showError(BaseMessages.getString(PKG, "SkillsManager.Editor.DescriptionRequired"));
      return;
    }

    String content = editorWidget.getText();
    String category = wCategory.getText();
    Skill.TriggerType trigger =
        wTrigger.getSelectionIndex() == 0 ? Skill.TriggerType.GLOBAL : Skill.TriggerType.MANUAL;
    int priority = wPriority.getSelection();
    String version = wVersion.getText().trim();
    if (version.isEmpty()) {
      version = "1.0.0";
    }
    boolean enabled = wEnabled.getSelection();

    if (isNew) {
      result = SkillManager.getInstance().create(name, description, content);
    } else {
      result = skill;
      result.setDescription(description);
      result.setContent(content);
    }

    result.setCategory(category);
    result.setTrigger(trigger);
    result.setPriority(priority);
    result.setVersion(version);
    result.setEnabled(enabled);

    // Persist all fields (create() only stored name/description/content + defaults).
    SkillManager.getInstance().update(result);

    dispose();
  }

  /** Cancels the dialog, discarding any changes. */
  private void cancel() {
    result = null;
    dispose();
  }

  /**
   * Shows an error message box.
   *
   * @param message the message to display
   */
  private void showError(String message) {
    MessageBox box = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
    box.setText(BaseMessages.getString(PKG, "SkillsManager.Editor.Title"));
    box.setMessage(message);
    box.open();
  }

  /** Stores the window geometry and disposes the shell. */
  private void dispose() {
    if (shell == null || shell.isDisposed()) {
      return;
    }
    props.setScreen(new WindowProperty(shell));
    shell.dispose();
  }
}
