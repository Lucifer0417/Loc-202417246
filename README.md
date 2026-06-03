# OOP Lab 05 - GUI Programming

Repository branch: `release/lab05`

This submission implements Lab 05 for the OOP course. The main work is the AIMS GUI migration, plus the standalone Swing/AWT and JavaFX practice exercises from the lab handout.

## What Is Included

### AIMS GUI Application

Location: `Lab02/AimsProject`

The AIMS project is kept under `Lab02/AimsProject` because later labs continue from the same source tree.

Implemented Lab 05 features:

- View Store screen implemented with Swing.
- View Cart screen implemented with JavaFX and loaded from `cart.fxml`.
- JavaFX cart screen is embedded in the Swing application through `JFXPanel`.
- Update Store menu supports adding Book, CD, and DVD.
- Cart table supports displaying media title, category, and cost.
- Cart filter supports filtering by ID or title.
- Cart buttons support Play, Remove, and Place Order.
- Cart total cost updates when items are added, removed, or ordered.
- Store screen supports Add to cart and Play actions.
- `PlayerException` is used for invalid playable media.
- `Media.equals()` compares media objects by title.
- Cart data uses `ObservableList<Media>` for JavaFX data-driven UI.

Important files:

```text
Lab02/AimsProject/src/hust/soict/dsai/aims/Aims.java
Lab02/AimsProject/src/hust/soict/dsai/aims/cart/Cart.java
Lab02/AimsProject/src/hust/soict/dsai/aims/screen/StoreScreen.java
Lab02/AimsProject/src/hust/soict/dsai/aims/screen/MediaStore.java
Lab02/AimsProject/src/hust/soict/dsai/aims/screen/CartScreen.java
Lab02/AimsProject/src/hust/soict/dsai/aims/screen/CartScreenController.java
Lab02/AimsProject/src/hust/soict/dsai/aims/screen/cart.fxml
Lab02/AimsProject/src/hust/soict/dsai/aims/screen/AddItemToStoreScreen.java
Lab02/AimsProject/src/hust/soict/dsai/aims/screen/AddBookToStoreScreen.java
Lab02/AimsProject/src/hust/soict/dsai/aims/screen/AddCompactDiscToStoreScreen.java
Lab02/AimsProject/src/hust/soict/dsai/aims/screen/AddDigitalVideoDiscToStoreScreen.java
Lab02/AimsProject/src/hust/soict/dsai/aims/exception/PlayerException.java
```

### Standalone GUI Exercises

Location: `Lab05/GUIProject`

Implemented exercises:

- `AWTAccumulator`
- `SwingAccumulator`
- `NumberGrid`
- JavaFX `Painter` app with Pen, Eraser, and Clear features

Important files:

```text
Lab05/GUIProject/src/hust/soict/dsai/swing/AWTAccumulator.java
Lab05/GUIProject/src/hust/soict/dsai/swing/SwingAccumulator.java
Lab05/GUIProject/src/hust/soict/dsai/swing/NumberGrid.java
Lab05/GUIProject/src/hust/soict/dsai/javafx/Painter.java
Lab05/GUIProject/src/hust/soict/dsai/javafx/PainterController.java
Lab05/GUIProject/src/hust/soict/dsai/javafx/Painter.fxml
```

### Written Answers And Diagrams

```text
Lab05/answers.txt
Lab02/Design/AIMS_Class.png
Lab02/Design/AIMS_Class.astah.asta
Lab02/Design/AIMS_Lab05_Astah_Class_Diagram.png
Lab02/Design/AIMS_Lab05_Astah_Exception_Hierarchy.png
```

## Requirements

- JDK 17 or newer
- JavaFX SDK

This branch was tested with:

```text
JDK 25.0.2
JavaFX SDK 26.0.1
```

If JavaFX is installed in another folder, change the `$javafx` path in the commands below.

## Run AIMS GUI

Open PowerShell at the repository root, for example:

```powershell
cd E:\OOP
```

Compile:

```powershell
$javafx = "E:\javafx-sdk-26.0.1\lib"
$out = "out\aims"
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Path $out | Out-Null
$sources = Get-ChildItem .\Lab02\AimsProject\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac --module-path $javafx --add-modules javafx.controls,javafx.fxml,javafx.swing -encoding UTF-8 -d $out $sources
```

Run:

```powershell
java --module-path $javafx --add-modules javafx.controls,javafx.fxml,javafx.swing -cp $out hust.soict.dsai.aims.Aims
```

Expected result:

- The AIMS Store window opens first.
- Use `Options > View cart` to open the JavaFX cart screen.
- Use `Options > Update store > Add book/CD/DVD` to add new media.

## Run JavaFX Painter

Open PowerShell at the repository root:

```powershell
cd E:\OOP
```

Compile:

```powershell
$javafx = "E:\javafx-sdk-26.0.1\lib"
$out = "out\gui"
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Path $out | Out-Null
$sources = Get-ChildItem .\Lab05\GUIProject\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac --module-path $javafx --add-modules javafx.controls,javafx.fxml -encoding UTF-8 -d $out $sources
```

Run:

```powershell
java --module-path $javafx --add-modules javafx.controls,javafx.fxml -cp $out hust.soict.dsai.javafx.Painter
```

Expected result:

- A Painter window opens.
- Drag the mouse on the white pane to draw.
- Select Eraser to erase using white ink.
- Press Clear to reset the canvas.

## Run Swing/AWT Practice Apps

Compile all GUIProject sources first:

```powershell
$javafx = "E:\javafx-sdk-26.0.1\lib"
$out = "out\gui"
$sources = Get-ChildItem .\Lab05\GUIProject\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac --module-path $javafx --add-modules javafx.controls,javafx.fxml -encoding UTF-8 -d $out $sources
```

Run one of the apps:

```powershell
java -cp $out hust.soict.dsai.swing.AWTAccumulator
java -cp $out hust.soict.dsai.swing.SwingAccumulator
java -cp $out hust.soict.dsai.swing.NumberGrid
```

## Eclipse Notes

If using Eclipse:

1. Import `Lab02/AimsProject` as a Java project.
2. Add JavaFX SDK libraries to the project build path.
3. Add VM arguments when running AIMS:

```text
--module-path E:\javafx-sdk-26.0.1\lib --add-modules javafx.controls,javafx.fxml,javafx.swing
```

For Painter, import or open `Lab05/GUIProject` and use:

```text
--module-path E:\javafx-sdk-26.0.1\lib --add-modules javafx.controls,javafx.fxml
```

## Validation

The following checks were run before submission:

```text
AIMS compile with JavaFX module path: passed
GUIProject compile with JavaFX module path: passed
cart.fxml XML parse: passed
Painter.fxml XML parse: passed
```

