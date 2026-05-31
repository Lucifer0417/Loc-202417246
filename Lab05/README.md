# Lab 05 - GUI Programming

This lab contains:

- `GUIProject`: AWT/Swing accumulator exercises, `NumberGrid`, and the JavaFX Painter exercise.
- `answers.txt`: short answers for the written questions.
- AIMS GUI implementation in `../Lab02/AimsProject`, because this repository keeps the shared AIMS project there and later labs build on the same codebase.

The AIMS GUI now follows the Lab 05 split:

- View Store: Swing (`StoreScreen`, `MediaStore`).
- View Cart: JavaFX loaded from `cart.fxml`, embedded in Swing with `JFXPanel`.
- Update Store: Swing add screens for Book, CD, and DVD.
- Diagrams: Lab 05 GUI overview and the self-defined exception hierarchy are in `../Lab02/Design`.

PowerShell build/run example for AIMS:

```powershell
$javafx = "E:\javafx-sdk-26.0.1\lib"
$out = "out\aims"
$sources = Get-ChildItem ..\Lab02\AimsProject\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac --module-path $javafx --add-modules javafx.controls,javafx.fxml,javafx.swing -d $out $sources
java --module-path $javafx --add-modules javafx.controls,javafx.fxml,javafx.swing -cp $out hust.soict.dsai.aims.Aims
```

PowerShell build/run example for Painter:

```powershell
$javafx = "E:\javafx-sdk-26.0.1\lib"
$out = "out\gui"
$sources = Get-ChildItem .\GUIProject\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac --module-path $javafx --add-modules javafx.controls,javafx.fxml -d $out $sources
java --module-path $javafx --add-modules javafx.controls,javafx.fxml -cp $out hust.soict.dsai.javafx.Painter
```
