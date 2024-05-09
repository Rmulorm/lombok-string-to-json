package com.rmaciel.lombokstringtojson;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

public class ConvertStringToJson extends AnAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    // Get all the required data from data keys
    Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
    Project project = event.getRequiredData(CommonDataKeys.PROJECT);
    Document document = editor.getDocument();

    // Work off of the primary caret to get the selection info
    Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
    int start = primaryCaret.getSelectionStart();
    int end = primaryCaret.getSelectionEnd();

    WriteCommandAction.runWriteCommandAction(
        project,
        () -> {
          String selected = document.getText(TextRange.from(start, end));
          document.replaceString(start, end, Converter.convert(selected));
        });

    primaryCaret.removeSelection();
  }

  @Override
  public void update(@NotNull AnActionEvent event) {
    Project project = event.getProject();
    Editor editor = event.getData(CommonDataKeys.EDITOR);

    event
        .getPresentation()
        .setEnabledAndVisible(
            project != null && editor != null && editor.getSelectionModel().hasSelection());
  }

  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return super.getActionUpdateThread();
  }
}
