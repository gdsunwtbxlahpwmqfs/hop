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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.dialog.MessageBox;
import org.apache.hop.ui.core.gui.GuiResource;
import org.apache.hop.ui.hopgui.assistant.skills.BuiltinSkills;
import org.apache.hop.ui.hopgui.assistant.skills.Skill;
import org.apache.hop.ui.hopgui.assistant.skills.SkillManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Resizable dialog for managing AI assistant skills.
 *
 * <p>Shows all loaded skills (built-in and custom) grouped by trigger type ({@link
 * Skill.TriggerType#GLOBAL} vs {@link Skill.TriggerType#MANUAL}), with search, category/sort
 * filters, and per-card enable/edit/duplicate/delete actions. All data operations go through {@link
 * SkillManager#getInstance()}.
 *
 * <p>A static {@link AtomicBoolean} ({@link #isOpen()}) tracks whether the dialog is currently on
 * screen so that sidebar buttons can reflect its state.
 */
public class SkillManagerDialog extends Dialog {

  private static final Class<?> PKG = SkillManager.class;

  private static final AtomicBoolean openFlag = new AtomicBoolean(false);

  private static final int MIN_WIDTH = 700;
  private static final int MIN_HEIGHT = 550;
  private static final int DESCRIPTION_LIMIT = 120;

  /** Category filter dropdown values, parallel to {@link #CATEGORY_LABELS}. Index 0 = "All". */
  private static final String[] CATEGORY_VALUES = {
    "all", "general", "pipeline", "database", "workflow", "custom"
  };

  /** Sort dropdown values, parallel to {@link #SORT_LABELS}. */
  private static final String[] SORT_VALUES = {"name", "usage"};

  private final SkillManager manager;
  private final BuiltinSkills builtinSkills;

  private Shell shell;
  private Text searchField;
  private Combo categoryFilter;
  private Combo sortFilter;
  private Button enabledOnlyCheckbox;
  private ScrolledComposite scrolledContainer;
  private Composite cardsContainer;
  private Label activeCountLabel;

  /** Re-entrancy guard so that {@link #recomputeMinSize()} cannot recurse. */
  private boolean layingOut;

  public SkillManagerDialog(Shell parent) {
    super(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
    this.manager = SkillManager.getInstance();
    this.builtinSkills = new BuiltinSkills();
  }

  /** Returns {@code true} while the dialog is open on screen. */
  public static boolean isOpen() {
    return openFlag.get();
  }

  /** Opens the dialog and runs the SWT event loop until the shell is disposed. */
  public void open() {
    Shell parent = getParent();
    Display display = parent.getDisplay();

    shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX | SWT.MODELESS);
    shell.setText(BaseMessages.getString(PKG, "SkillsManager.Dialog.Title"));
    FormLayout shellLayout = new FormLayout();
    shellLayout.marginWidth = 0;
    shellLayout.marginHeight = 0;
    shell.setLayout(shellLayout);
    PropsUi.setLook(shell);
    shell.setMinimumSize(MIN_WIDTH, MIN_HEIGHT);

    manager.loadAll();

    createContent();
    refreshCards();

    shell.setSize(MIN_WIDTH + 100, MIN_HEIGHT + 100);
    positionCentered(parent);
    openFlag.set(true);

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    openFlag.set(false);
  }

  private void positionCentered(Shell parent) {
    Rectangle clientArea;
    Monitor monitor = parent.getMonitor();
    if (monitor != null) {
      clientArea = monitor.getClientArea();
    } else {
      clientArea = parent.getDisplay().getBounds();
    }
    Point size = shell.getSize();
    int x = clientArea.x + (clientArea.width - size.x) / 2;
    int y = clientArea.y + (clientArea.height - size.y) / 2;
    shell.setLocation(Math.max(clientArea.x, x), Math.max(clientArea.y, y));
  }

  private void closeDialog() {
    if (shell != null && !shell.isDisposed()) {
      shell.dispose();
    }
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Layout construction
  // ──────────────────────────────────────────────────────────────────────────────

  private void createContent() {
    Composite toolbar = createToolbar();
    Composite footer = createFooter();
    createScrolledCards(toolbar, footer);
  }

  private Composite createToolbar() {
    Composite toolbar = new Composite(shell, SWT.NONE);
    FormData fdToolbar = new FormData();
    fdToolbar.left = new FormAttachment(0, 0);
    fdToolbar.right = new FormAttachment(100, 0);
    fdToolbar.top = new FormAttachment(0, 0);
    toolbar.setLayoutData(fdToolbar);
    FormLayout tbLayout = new FormLayout();
    tbLayout.marginWidth = PropsUi.getMargin();
    tbLayout.marginHeight = PropsUi.getMargin() / 2;
    toolbar.setLayout(tbLayout);
    PropsUi.setLook(toolbar);

    // ---- Search field ----
    searchField = new Text(toolbar, SWT.SINGLE | SWT.BORDER | SWT.SEARCH);
    FormData fdSearch = new FormData();
    fdSearch.left = new FormAttachment(0, 0);
    fdSearch.right = new FormAttachment(70, -PropsUi.getMargin() / 2);
    fdSearch.top = new FormAttachment(0, 0);
    searchField.setLayoutData(fdSearch);
    searchField.setMessage(BaseMessages.getString(PKG, "SkillsManager.Dialog.SearchPlaceholder"));
    PropsUi.setLook(searchField);
    searchField.addListener(SWT.Modify, e -> refreshCards());

    // ---- New Skill button ----
    Button newSkillButton = new Button(toolbar, SWT.PUSH);
    newSkillButton.setText(BaseMessages.getString(PKG, "SkillsManager.Button.New"));
    FormData fdNew = new FormData();
    fdNew.left = new FormAttachment(searchField, PropsUi.getMargin());
    fdNew.right = new FormAttachment(100, 0);
    fdNew.top = new FormAttachment(searchField, 0, SWT.TOP);
    fdNew.bottom = new FormAttachment(searchField, 0, SWT.BOTTOM);
    newSkillButton.setLayoutData(fdNew);
    PropsUi.setLook(newSkillButton);
    newSkillButton.addListener(SWT.Selection, e -> openEditor(null));

    // ---- Category filter row ----
    Label categoryLabel = new Label(toolbar, SWT.NONE);
    categoryLabel.setText(BaseMessages.getString(PKG, "SkillsManager.Filter.Category"));
    PropsUi.setLook(categoryLabel);
    FormData fdCatLbl = new FormData();
    fdCatLbl.left = new FormAttachment(0, 0);
    fdCatLbl.top = new FormAttachment(searchField, PropsUi.getMargin(), SWT.BOTTOM);
    categoryLabel.setLayoutData(fdCatLbl);

    categoryFilter = new Combo(toolbar, SWT.READ_ONLY);
    categoryFilter.add(BaseMessages.getString(PKG, "SkillsManager.Label.All"));
    categoryFilter.add(BaseMessages.getString(PKG, "SkillsManager.Label.General"));
    categoryFilter.add(BaseMessages.getString(PKG, "SkillsManager.Label.Pipeline"));
    categoryFilter.add(BaseMessages.getString(PKG, "SkillsManager.Label.Database"));
    categoryFilter.add(BaseMessages.getString(PKG, "SkillsManager.Label.Workflow"));
    categoryFilter.add(BaseMessages.getString(PKG, "SkillsManager.Label.Custom"));
    categoryFilter.select(0);
    FormData fdCat = new FormData();
    fdCat.left = new FormAttachment(categoryLabel, PropsUi.getMargin());
    fdCat.top = new FormAttachment(searchField, PropsUi.getMargin(), SWT.BOTTOM);
    categoryFilter.setLayoutData(fdCat);
    PropsUi.setLook(categoryFilter);
    categoryFilter.addListener(SWT.Selection, e -> refreshCards());

    // ---- Sort filter ----
    Label sortLabel = new Label(toolbar, SWT.NONE);
    sortLabel.setText(BaseMessages.getString(PKG, "SkillsManager.Filter.Sort"));
    PropsUi.setLook(sortLabel);
    FormData fdSortLbl = new FormData();
    fdSortLbl.left = new FormAttachment(categoryFilter, PropsUi.getMargin() * 2);
    fdSortLbl.top = new FormAttachment(searchField, PropsUi.getMargin(), SWT.BOTTOM);
    sortLabel.setLayoutData(fdSortLbl);

    sortFilter = new Combo(toolbar, SWT.READ_ONLY);
    sortFilter.add(BaseMessages.getString(PKG, "SkillsManager.Sort.Name"));
    sortFilter.add(BaseMessages.getString(PKG, "SkillsManager.Sort.Usage"));
    sortFilter.select(0);
    FormData fdSort = new FormData();
    fdSort.left = new FormAttachment(sortLabel, PropsUi.getMargin());
    fdSort.top = new FormAttachment(searchField, PropsUi.getMargin(), SWT.BOTTOM);
    sortFilter.setLayoutData(fdSort);
    PropsUi.setLook(sortFilter);
    sortFilter.addListener(SWT.Selection, e -> refreshCards());

    // ---- Enabled-only checkbox ----
    enabledOnlyCheckbox = new Button(toolbar, SWT.CHECK);
    enabledOnlyCheckbox.setText(BaseMessages.getString(PKG, "SkillsManager.Label.EnabledOnly"));
    FormData fdCheck = new FormData();
    fdCheck.left = new FormAttachment(sortFilter, PropsUi.getMargin() * 2);
    fdCheck.top = new FormAttachment(searchField, PropsUi.getMargin(), SWT.BOTTOM);
    enabledOnlyCheckbox.setLayoutData(fdCheck);
    PropsUi.setLook(enabledOnlyCheckbox);
    enabledOnlyCheckbox.addListener(SWT.Selection, e -> refreshCards());

    return toolbar;
  }

  private Composite createFooter() {
    Composite footer = new Composite(shell, SWT.NONE);
    FormData fdFooter = new FormData();
    fdFooter.left = new FormAttachment(0, 0);
    fdFooter.right = new FormAttachment(100, 0);
    fdFooter.bottom = new FormAttachment(100, 0);
    footer.setLayoutData(fdFooter);
    FormLayout fLayout = new FormLayout();
    fLayout.marginWidth = PropsUi.getMargin();
    fLayout.marginHeight = PropsUi.getMargin() / 2;
    footer.setLayout(fLayout);
    PropsUi.setLook(footer);

    activeCountLabel = new Label(footer, SWT.NONE);
    FormData fdCount = new FormData();
    fdCount.left = new FormAttachment(0, 0);
    fdCount.top = new FormAttachment(0, 0);
    fdCount.bottom = new FormAttachment(100, 0);
    activeCountLabel.setLayoutData(fdCount);
    activeCountLabel.setFont(GuiResource.getInstance().getFontBold());
    PropsUi.setLook(activeCountLabel);

    Button restoreButton = new Button(footer, SWT.PUSH);
    restoreButton.setText(BaseMessages.getString(PKG, "SkillsManager.Button.RestoreBuiltin"));
    FormData fdRestore = new FormData();
    fdRestore.right = new FormAttachment(100, 0);
    fdRestore.top = new FormAttachment(0, 0);
    fdRestore.bottom = new FormAttachment(100, 0);
    fdRestore.width = PropsUi.getMargin() * 24;
    restoreButton.setLayoutData(fdRestore);
    PropsUi.setLook(restoreButton);
    restoreButton.addListener(SWT.Selection, e -> restoreBuiltinSkills());

    return footer;
  }

  private void createScrolledCards(Composite toolbar, Composite footer) {
    scrolledContainer = new ScrolledComposite(shell, SWT.V_SCROLL);
    FormData fdScroll = new FormData();
    fdScroll.left = new FormAttachment(0, 0);
    fdScroll.right = new FormAttachment(100, 0);
    fdScroll.top = new FormAttachment(toolbar, 0);
    fdScroll.bottom = new FormAttachment(footer, 0);
    scrolledContainer.setLayoutData(fdScroll);
    scrolledContainer.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    PropsUi.setLook(scrolledContainer);

    cardsContainer = new Composite(scrolledContainer, SWT.NONE);
    GridLayout cl = new GridLayout(1, false);
    cl.marginWidth = PropsUi.getMargin();
    cl.marginHeight = PropsUi.getMargin();
    cl.verticalSpacing = PropsUi.getMargin() / 2;
    cardsContainer.setLayout(cl);
    cardsContainer.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    PropsUi.setLook(cardsContainer);

    scrolledContainer.setContent(cardsContainer);
    scrolledContainer.setExpandHorizontal(true);
    scrolledContainer.setExpandVertical(true);
    scrolledContainer.addListener(SWT.Resize, e -> recomputeMinSize());
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Card list
  // ──────────────────────────────────────────────────────────────────────────────

  /** Rebuilds the whole card list from {@link SkillManager} applying the current filters. */
  private void refreshCards() {
    if (cardsContainer == null || cardsContainer.isDisposed()) {
      return;
    }

    for (Control child : cardsContainer.getChildren()) {
      child.dispose();
    }

    List<Skill> filtered = getFilteredSkills();
    List<Skill> global =
        filtered.stream()
            .filter(s -> s.getTrigger() == Skill.TriggerType.GLOBAL)
            .sorted(Comparator.comparingInt(Skill::getPriority).reversed())
            .toList();
    List<Skill> manual =
        filtered.stream()
            .filter(s -> s.getTrigger() == Skill.TriggerType.MANUAL)
            .sorted(Comparator.comparing(Skill::getName, String.CASE_INSENSITIVE_ORDER))
            .toList();

    if (!global.isEmpty()) {
      createSectionHeader(
          cardsContainer, BaseMessages.getString(PKG, "SkillsManager.Section.Global"));
      global.forEach(s -> createSkillCard(cardsContainer, s));
    }
    if (!manual.isEmpty()) {
      createSectionHeader(
          cardsContainer, BaseMessages.getString(PKG, "SkillsManager.Section.Manual"));
      manual.forEach(s -> createSkillCard(cardsContainer, s));
    }
    if (global.isEmpty() && manual.isEmpty()) {
      Label empty = new Label(cardsContainer, SWT.WRAP);
      empty.setText(BaseMessages.getString(PKG, "SkillsAssistant.NoSkills"));
      empty.setLayoutData(fillGridData(true, false));
      PropsUi.setLook(empty);
    }

    updateActiveCount();
    recomputeMinSize();
  }

  private void createSectionHeader(Composite parent, String text) {
    Label header = new Label(parent, SWT.NONE);
    header.setText(text);
    header.setFont(GuiResource.getInstance().getFontBold());
    header.setForeground(GuiResource.getInstance().getColorBlue());
    GridData gd = fillGridData(true, false);
    gd.verticalIndent = PropsUi.getMargin();
    header.setLayoutData(gd);
    PropsUi.setLook(header);
  }

  private void createSkillCard(Composite parent, Skill skill) {
    Composite card = new Composite(parent, SWT.BORDER);
    card.setLayoutData(fillGridData(true, false));
    GridLayout cardLayout = new GridLayout(2, false);
    cardLayout.marginWidth = PropsUi.getMargin();
    cardLayout.marginHeight = PropsUi.getMargin() / 2;
    cardLayout.horizontalSpacing = PropsUi.getMargin();
    cardLayout.verticalSpacing = 2;
    card.setLayout(cardLayout);
    card.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    PropsUi.setLook(card);

    // ── Left column: skill info ──
    Composite info = new Composite(card, SWT.NONE);
    info.setLayoutData(fillGridData(true, true));
    GridLayout infoLayout = new GridLayout(1, false);
    infoLayout.marginWidth = 0;
    infoLayout.marginHeight = 0;
    infoLayout.verticalSpacing = 3;
    info.setLayout(infoLayout);
    info.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    PropsUi.setLook(info);

    // Name + badges row
    Composite nameRow = new Composite(info, SWT.NONE);
    nameRow.setLayoutData(fillGridData(true, false));
    RowLayout nameRowLayout = new RowLayout(SWT.HORIZONTAL);
    nameRowLayout.marginTop = 0;
    nameRowLayout.marginBottom = 0;
    nameRowLayout.marginLeft = 0;
    nameRowLayout.marginRight = 0;
    nameRowLayout.center = true;
    nameRowLayout.spacing = PropsUi.getMargin() / 2;
    nameRow.setLayout(nameRowLayout);
    PropsUi.setLook(nameRow);

    Label nameLabel = new Label(nameRow, SWT.NONE);
    nameLabel.setText(skill.getName());
    nameLabel.setFont(GuiResource.getInstance().getFontBold());
    PropsUi.setLook(nameLabel);

    createBadge(
        nameRow,
        skill.getCategory(),
        badgeForeground(skill.getCategory()),
        badgeBackground(skill.getCategory()));
    createBadge(
        nameRow,
        BaseMessages.getString(
            PKG,
            skill.getTrigger() == Skill.TriggerType.GLOBAL
                ? "SkillsManager.Label.Global"
                : "SkillsManager.Label.Manual"),
        skill.getTrigger() == Skill.TriggerType.GLOBAL
            ? GuiResource.getInstance().getColorWhite()
            : GuiResource.getInstance().getColorWhite(),
        skill.getTrigger() == Skill.TriggerType.GLOBAL
            ? GuiResource.getInstance().getColorDarkGreen()
            : GuiResource.getInstance().getColorGray());
    createBadge(
        nameRow,
        BaseMessages.getString(
            PKG, skill.isBuiltin() ? "SkillsManager.Label.Builtin" : "SkillsManager.Label.Custom"),
        GuiResource.getInstance().getColorWhite(),
        skill.isBuiltin()
            ? GuiResource.getInstance().getColorOrange()
            : GuiResource.getInstance().getColorIndigo());

    // Description (truncated)
    Label descLabel = new Label(info, SWT.WRAP);
    descLabel.setLayoutData(fillGridData(true, false));
    descLabel.setText(truncate(skill.getDescription(), DESCRIPTION_LIMIT));
    PropsUi.setLook(descLabel);

    // Usage count
    Label usageLabel = new Label(info, SWT.NONE);
    usageLabel.setLayoutData(fillGridData(true, false));
    usageLabel.setText(
        BaseMessages.getString(PKG, "SkillsManager.Label.Usage", skill.getUsageCount()));
    usageLabel.setForeground(GuiResource.getInstance().getColorDarkGray());
    PropsUi.setLook(usageLabel);

    // ── Right column: action buttons ──
    Composite actions = new Composite(card, SWT.NONE);
    GridData actionsGd = new GridData(SWT.FILL, SWT.TOP, false, true);
    actions.setLayoutData(actionsGd);
    GridLayout actionsLayout = new GridLayout(1, false);
    actionsLayout.marginWidth = 0;
    actionsLayout.marginHeight = 0;
    actionsLayout.verticalSpacing = 2;
    actions.setLayout(actionsLayout);
    PropsUi.setLook(actions);

    Button toggleButton = new Button(actions, SWT.PUSH);
    toggleButton.setLayoutData(fillGridData(true, false));
    toggleButton.setText(
        BaseMessages.getString(
            PKG,
            skill.isEnabled() ? "SkillsManager.Button.Disable" : "SkillsManager.Button.Enable"));
    toggleButton.addListener(
        SWT.Selection,
        e -> {
          manager.toggleEnabled(skill.getName());
          refreshCards();
        });
    PropsUi.setLook(toggleButton);

    Button editButton = new Button(actions, SWT.PUSH);
    editButton.setLayoutData(fillGridData(true, false));
    editButton.setText(BaseMessages.getString(PKG, "SkillsManager.Button.Edit"));
    editButton.addListener(SWT.Selection, e -> openEditor(skill));
    PropsUi.setLook(editButton);

    Button duplicateButton = new Button(actions, SWT.PUSH);
    duplicateButton.setLayoutData(fillGridData(true, false));
    duplicateButton.setText(BaseMessages.getString(PKG, "SkillsManager.Button.Duplicate"));
    duplicateButton.addListener(SWT.Selection, e -> duplicateSkill(skill));
    PropsUi.setLook(duplicateButton);

    // Delete is hidden for built-in skills.
    if (!skill.isBuiltin()) {
      Button deleteButton = new Button(actions, SWT.PUSH);
      deleteButton.setLayoutData(fillGridData(true, false));
      deleteButton.setText(BaseMessages.getString(PKG, "SkillsManager.Button.Delete"));
      deleteButton.addListener(SWT.Selection, e -> deleteSkill(skill));
      PropsUi.setLook(deleteButton);
    }
  }

  private void createBadge(Composite parent, String text, Color foreground, Color background) {
    if (text == null || text.isBlank()) {
      return;
    }
    Label badge = new Label(parent, SWT.NONE);
    badge.setText(" " + text + " ");
    badge.setForeground(foreground);
    badge.setBackground(background);
    badge.setFont(GuiResource.getInstance().getFontSmall());
  }

  private Color badgeForeground(@SuppressWarnings("unused") String category) {
    return GuiResource.getInstance().getColorWhite();
  }

  private Color badgeBackground(String category) {
    if ("pipeline".equalsIgnoreCase(category)) {
      return GuiResource.getInstance().getColorIndigo();
    }
    if ("general".equalsIgnoreCase(category)) {
      return GuiResource.getInstance().getColorBlue();
    }
    if ("custom".equalsIgnoreCase(category)) {
      return GuiResource.getInstance().getColorGray();
    }
    return GuiResource.getInstance().getColorDarkGray();
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Actions
  // ──────────────────────────────────────────────────────────────────────────────

  private void openEditor(Skill skill) {
    SkillEditorDialog editor = new SkillEditorDialog(shell, skill);
    editor.open();
    // The editor persists changes; reload and refresh the list.
    manager.loadAll();
    refreshCards();
  }

  private void duplicateSkill(Skill skill) {
    String baseName = skill.getName() + "-copy";
    String newName = baseName;
    int suffix = 1;
    while (manager.find(newName) != null) {
      newName = baseName + "-" + suffix++;
    }
    manager.duplicate(skill.getName(), newName);
    refreshCards();
  }

  private void deleteSkill(Skill skill) {
    if (skill.isBuiltin()) {
      MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_WARNING);
      box.setText(BaseMessages.getString(PKG, "SkillsManager.Message.DeleteTitle"));
      box.setMessage(BaseMessages.getString(PKG, "SkillsManager.Message.CannotDeleteBuiltin"));
      box.open();
      return;
    }
    MessageBox box = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
    box.setText(BaseMessages.getString(PKG, "SkillsManager.Message.DeleteTitle"));
    box.setMessage(
        BaseMessages.getString(PKG, "SkillsManager.Message.DeleteConfirm", skill.getName()));
    if (box.open() == SWT.YES) {
      manager.delete(skill.getName());
      refreshCards();
    }
  }

  private void restoreBuiltinSkills() {
    MessageBox box = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
    box.setText(BaseMessages.getString(PKG, "SkillsManager.Dialog.Title"));
    box.setMessage(BaseMessages.getString(PKG, "SkillsManager.Message.RestoreConfirm"));
    if (box.open() == SWT.YES) {
      builtinSkills.restoreAll(SkillManager.PROJECT_SKILLS_DIR);
      manager.loadAll();
      refreshCards();
    }
  }

  private void updateActiveCount() {
    if (activeCountLabel == null || activeCountLabel.isDisposed()) {
      return;
    }
    int active = manager.getActiveSkills().size();
    int total = manager.getAll().size();
    activeCountLabel.setText(
        BaseMessages.getString(PKG, "SkillsManager.Label.ActiveSkills", active, total));
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Filtering helpers
  // ──────────────────────────────────────────────────────────────────────────────

  private List<Skill> getFilteredSkills() {
    String query = searchField == null || searchField.isDisposed() ? "" : searchField.getText();
    String category = selectedCategoryValue();
    Boolean enabledOnly =
        (enabledOnlyCheckbox != null
                && !enabledOnlyCheckbox.isDisposed()
                && enabledOnlyCheckbox.getSelection())
            ? Boolean.TRUE
            : null;
    List<Skill> result = manager.search(query, category, enabledOnly);
    // search() returns an immutable list (Stream.toList()); copy before sorting
    List<Skill> sortable = new ArrayList<>(result);
    if ("usage".equals(selectedSortValue())) {
      sortable.sort(Comparator.comparingInt(Skill::getUsageCount).reversed());
    } else {
      sortable.sort(Comparator.comparing(Skill::getName, String.CASE_INSENSITIVE_ORDER));
    }
    return sortable;
  }

  private String selectedCategoryValue() {
    if (categoryFilter == null || categoryFilter.isDisposed()) {
      return CATEGORY_VALUES[0];
    }
    int index = categoryFilter.getSelectionIndex();
    return (index >= 0 && index < CATEGORY_VALUES.length)
        ? CATEGORY_VALUES[index]
        : CATEGORY_VALUES[0];
  }

  private String selectedSortValue() {
    if (sortFilter == null || sortFilter.isDisposed()) {
      return SORT_VALUES[0];
    }
    int index = sortFilter.getSelectionIndex();
    return (index >= 0 && index < SORT_VALUES.length) ? SORT_VALUES[index] : SORT_VALUES[0];
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Utilities
  // ──────────────────────────────────────────────────────────────────────────────

  private void recomputeMinSize() {
    if (layingOut) {
      return;
    }
    if (scrolledContainer == null
        || scrolledContainer.isDisposed()
        || cardsContainer == null
        || cardsContainer.isDisposed()) {
      return;
    }
    layingOut = true;
    try {
      cardsContainer.layout(true);
      int width = scrolledContainer.getClientArea().width;
      if (width <= 0) {
        width = MIN_WIDTH;
      }
      Point size = cardsContainer.computeSize(width, SWT.DEFAULT, true);
      scrolledContainer.setMinSize(width, size.y);
    } finally {
      layingOut = false;
    }
  }

  private static GridData fillGridData(boolean grabHorizontal, boolean grabVertical) {
    return new GridData(SWT.FILL, SWT.FILL, grabHorizontal, grabVertical);
  }

  private static String truncate(String text, int max) {
    if (text == null || text.isEmpty()) {
      return "";
    }
    String single = text.replaceAll("\\s+", " ").trim();
    if (single.length() <= max) {
      return single;
    }
    return single.substring(0, max) + "...";
  }
}
