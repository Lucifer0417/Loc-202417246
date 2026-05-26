# Lab 05 - GUI Programming

This lab contains:

- `GUIProject`: standalone AWT/Swing exercises from the lab handout.
- `answers.txt`: short answers for the written questions.
- AIMS GUI implementation: updated in `../Lab02/AimsProject` because this repository keeps the course AIMS project there and later labs build on the same codebase.

Run the AIMS GUI from:

```sh
javac -d out $(find ../Lab02/AimsProject/src -name "*.java")
java -cp out hust.soict.dsai.aims.Aims
```

On Windows PowerShell:

```powershell
$sources = Get-ChildItem ..\Lab02\AimsProject\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $sources
java -cp out hust.soict.dsai.aims.Aims
```
