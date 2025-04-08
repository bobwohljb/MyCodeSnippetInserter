# Kotlin Code Snippet Inserter

An IntelliJ IDEA plugin that provides quick insertion of common Kotlin code patterns.

## Features

This plugin adds a "My Kotlin Snippets" group to the Code menu, allowing you to quickly insert:

1. **Data Class Templates** - Insert a fully-featured Kotlin data class with properties, methods, and a companion object.
2. **Singleton Patterns** - Insert a Kotlin singleton implementation using object declaration.
3. **Extension Functions** - Insert common Kotlin extension function patterns for strings, collections, and more.
4. **Coroutine Patterns** - Insert Kotlin coroutine patterns for asynchronous programming.

## Usage

1. Open a Kotlin file in the editor
2. Place the cursor where you want to insert the code
3. Either:
   - Go to Code → My Kotlin Snippets and select the pattern you want to insert, or
   - Use one of the keyboard shortcuts:
     - **Alt+Shift+D** - Insert Data Class
     - **Alt+Shift+S** - Insert Singleton
     - **Alt+Shift+E** - Insert Extension Functions
     - **Alt+Shift+C** - Insert Coroutine Patterns

## Requirements

- IntelliJ IDEA 2024.1 or later
- Kotlin plugin installed

## Building from Source

This project uses Gradle for building. To build the plugin:

```bash
./gradlew build
```

The plugin JAR file will be created in the `build/libs` directory.

## Development

The plugin consists of:

- `EditorHandler` - Base class for handling editor interactions
- Four specific implementations for different Kotlin patterns
- Plugin configuration in `plugin.xml`

## License

This project is licensed under the MIT License - see the LICENSE file for details.
