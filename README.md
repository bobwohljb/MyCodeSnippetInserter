# Kotlin Code Snippet Inserter

An IntelliJ IDEA plugin that provides quick insertion of common Kotlin code patterns.

**Current Version:** 1.0-SNAPSHOT

## Features

This plugin provides multiple ways to access common Kotlin code patterns:

1. **Data Class Templates** - Insert a fully-featured Kotlin data class with properties, methods, and a companion object.
2. **Singleton Patterns** - Insert a Kotlin singleton implementation using object declaration.
3. **Extension Functions** - Insert common Kotlin extension function patterns for strings, collections, and more.
4. **Coroutine Patterns** - Insert Kotlin coroutine patterns for asynchronous programming.

You can access these patterns through:
- The "My Kotlin Snippets" group in the Code menu (for inserting into existing files)
- A dedicated tool window (for creating new files with snippets)
- A popup dialog from the Tools menu

## Usage

### Inserting Code Snippets

To insert code snippets into an existing file:

1. Open a Kotlin file in the editor
2. Place the cursor where you want to insert the code
3. Choose one of these methods:
   - Go to Code → My Kotlin Snippets and select the pattern you want to insert
   - Use one of the keyboard shortcuts:
     - **Alt+Shift+D** - Insert Data Class
     - **Alt+Shift+S** - Insert Singleton
     - **Alt+Shift+E** - Insert Extension Functions
     - **Alt+Shift+C** - Insert Coroutine Patterns
   - Go to Tools → MyKotlinInserter to open a popup dialog with all available patterns

### Creating New Files with Snippets

To create new files that already contain the code snippets:

1. Open the MyKotlinInserter tool window (right sidebar)
2. Click the "Create File" button for the desired pattern
3. Enter a name for the new file when prompted
4. The new file will be created and opened in the editor

## Requirements

- IntelliJ IDEA 2024.1.7 or later (compatible with versions up to 2024.3.*)
- Kotlin plugin installed
- Java 17 or later

## Building from Source

This project uses Gradle for building. To build the plugin:

```bash
./gradlew build
```

The plugin JAR file will be created in the `build/libs` directory.

## Development

The plugin consists of:

- `EditorHandler` - Base class for handling editor interactions
- Four specific implementations for different Kotlin patterns:
  - `DataClassHandler`
  - `SingletonHandler`
  - `ExtensionFunctionHandler`
  - `CoroutineHandler`
- Tool window components:
  - `MyKotlinInserterToolWindowFactory` - Factory for creating the tool window
  - `MyKotlinInserterToolWindow` - UI component for the tool window
- Additional components:
  - `MyKotlinInserterAction` - Action for showing a popup dialog with all available patterns
  - `FileCreator` - Utility for creating new files with code snippets
- Plugin configuration in `plugin.xml`

## License

This project is licensed under the MIT License - see the LICENSE file for details.
