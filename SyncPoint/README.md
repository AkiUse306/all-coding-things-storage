# SyncPoint - Cross-Platform File Manager

![SyncPoint Logo](https://img.shields.io/badge/SyncPoint-File%20Manager-blue)
![License](https://img.shields.io/badge/license-MIT-green)
![Version](https://img.shields.io/badge/version-1.0.0-brightgreen)
![Maintained](https://img.shields.io/badge/maintained%3F-yes-success)

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Usage](#usage)
- [Contributing](#contributing)
- [Code of Conduct](#code-of-conduct)
- [License](#license)
- [Support](#support)

## 🎯 Project Overview

**SyncPoint** is a modern, lightweight, and intuitive cross-platform file manager designed to streamline file operations across Windows, macOS, and Linux. Built with performance and user experience in mind, SyncPoint provides seamless file synchronization, advanced search capabilities, and intelligent organization tools for users who work with multiple devices and require reliable file management.

Whether you're a developer managing project files, a creative professional organizing assets, or a business user handling documentation, SyncPoint simplifies file management with an elegant interface and powerful backend capabilities.

### Key Highlights
- 🚀 **Fast & Responsive**: Optimized for performance with instant file operations
- 🔄 **Smart Sync**: Automatic file synchronization across devices
- 🔍 **Advanced Search**: Intelligent search with multiple filter options
- 🎨 **Modern UI**: Clean, intuitive interface following platform conventions
- 🔐 **Secure**: End-to-end encryption for sensitive files
- ⚡ **Lightweight**: Minimal resource footprint with maximum functionality

## ✨ Features

### Core Features
- **Multi-Platform Support**: Native applications for Windows, macOS, and Linux
- **Dual-Pane Interface**: Side-by-side directory navigation for efficient file operations
- **Drag & Drop Support**: Intuitive file movement and organization
- **Quick Preview**: Built-in file preview for images, documents, and media
- **Tabbed Navigation**: Multiple directory tabs for seamless workflow
- **Keyboard Shortcuts**: Comprehensive keyboard support for power users

### Advanced Features
- **File Synchronization**: One-way and bi-directional sync between directories
- **Smart Search**: Full-text search with regex support and advanced filters
- **Batch Operations**: Rename, move, copy, and delete multiple files simultaneously
- **Custom Filters**: Create and save custom file filtering rules
- **File Bookmarks**: Quick access to frequently used directories
- **Undo/Redo**: Full operation history with undo/redo capabilities
- **Archive Support**: Built-in support for ZIP, TAR, 7Z, and RAR formats
- **Cloud Integration**: Native integration with major cloud storage providers

### Organization Tools
- **Auto-Categorization**: Automatically organize files by type, date, or custom rules
- **File Tags**: Add custom tags for better file organization and retrieval
- **Color Labels**: Visual organization using color-coded labels
- **Duplicate Finder**: Identify and manage duplicate files efficiently
- **Space Analyzer**: Visualize disk usage and identify space-consuming files

## 🏗️ Architecture

### System Architecture

```
┌─────────────────────────────────────────────────────┐
│            User Interface Layer (UI)                │
│  ┌────────────┐  ┌────────────┐  ┌──────────────┐ │
│  │   Main    │  │  File Tree │  │  Preview    │ │
│  │  Window   │  │  View      │  │  Panel      │ │
│  └────────────┘  └────────────┘  └──────────────┘ │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────┐
│        Business Logic Layer (Services)              │
│  ┌──────────────────────────────────────────────┐  │
│  │  File Manager  │  Search Engine  │  Sync    │  │
│  │  Service       │  Service        │  Service  │  │
│  └──────────────────────────────────────────────┘  │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────┐
│      Data Access Layer (File System, API)          │
│  ┌────────────┐  ┌────────────┐  ┌──────────────┐ │
│  │ File I/O  │  │ Database   │  │  Cloud API   │ │
│  │ Operations│  │ Layer      │  │  Integration │ │
│  └────────────┘  └────────────┘  └──────────────┘ │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────┐
│      System Layer (OS APIs & Services)              │
│  File System  │  Notifications  │  Security       │
└─────────────────────────────────────────────────────┘
```

### Component Breakdown

| Component | Responsibility |
|-----------|----------------|
| **UI Layer** | Renders user interface and handles user interactions |
| **Service Layer** | Implements business logic and core functionality |
| **Data Access** | Manages file I/O, database queries, and external API calls |
| **System Layer** | Interfaces with operating system and platform-specific features |

### Module Dependency Graph

```
main
├── ui
│   ├── components
│   ├── services
│   └── utils
├── core
│   ├── file-manager
│   ├── sync-engine
│   ├── search-engine
│   └── archive-handler
├── services
│   ├── cloud-integration
│   ├── encryption
│   └── notifications
├── database
│   ├── models
│   └── migrations
└── utils
    ├── logger
    ├── validators
    └── helpers
```

## 🛠️ Tech Stack

### Frontend
- **Framework**: Electron.js v20.0+
- **UI Library**: React 18.x with TypeScript
- **Styling**: CSS-in-JS (Styled Components) with Tailwind CSS
- **State Management**: Redux Toolkit for global state
- **Component Library**: Material-UI v5.x

### Backend
- **Runtime**: Node.js v18.0+
- **File Operations**: Native Node.js fs module with custom abstractions
- **Database**: SQLite for metadata and settings
- **API Client**: Axios for HTTP requests
- **Real-time Monitoring**: Chokidar for file system watching

### Development Tools
- **Package Manager**: npm v9.0+
- **Build Tool**: Webpack 5.x
- **Module Bundler**: Rollup for library builds
- **Testing**: Jest & React Testing Library
- **Linting**: ESLint with Prettier
- **Type Checking**: TypeScript 5.x
- **Documentation**: JSDoc with automated generation

### DevOps & Deployment
- **Version Control**: Git
- **CI/CD**: GitHub Actions
- **Release Management**: Electron Builder
- **Code Coverage**: Codecov
- **Documentation Hosting**: GitHub Pages

### Optional Integrations
- **Cloud Storage**: AWS S3, Google Drive, OneDrive APIs
- **Encryption**: OpenSSL, libsodium
- **Compression**: 7-Zip, p7zip libraries

## 📦 Installation

### Prerequisites

Before installing SyncPoint, ensure you have the following:

- **Node.js**: v18.0.0 or higher ([Download](https://nodejs.org/))
- **npm**: v9.0.0 or higher (typically installed with Node.js)
- **Git**: For cloning the repository
- **Operating System**: Windows 10+, macOS 10.13+, or Linux (Ubuntu 20.04+)

### Clone the Repository

```bash
git clone https://github.com/AkiUse306/SyncPoint.git
cd SyncPoint
```

### Install Dependencies

```bash
# Install all project dependencies
npm install

# For development with hot reload
npm run dev

# For production build
npm run build
```

### Verify Installation

```bash
# Run tests to verify installation
npm test

# Check application version
npm run version
```

### Build Application

#### For Windows
```bash
npm run build:win
# Creates installer in dist/ directory
```

#### For macOS
```bash
npm run build:mac
# Creates DMG file in dist/ directory
```

#### For Linux
```bash
npm run build:linux
# Creates AppImage and deb files in dist/ directory
```

## 🚀 Quick Start

### 1. Launch the Application

```bash
# Development mode
npm start

# Production mode
./dist/SyncPoint
```

### 2. First-Time Setup

1. Accept the End-User License Agreement
2. Choose installation preferences:
   - Theme (Light/Dark)
   - Default workspace directory
   - Cloud integration (optional)
3. Create your first workspace

### 3. Basic Operations

#### Opening a Directory
```
File → Open Directory
or
Press Ctrl+O (Cmd+O on macOS)
```

#### Navigating Files
- **Single Pane**: Click directory entries to navigate
- **Dual Pane**: Use left and right panels for comparison
- **Breadcrumb Navigation**: Click path components to jump directly

#### File Operations
- **Copy**: Ctrl+C
- **Cut**: Ctrl+X
- **Paste**: Ctrl+V
- **Delete**: Delete key
- **Rename**: F2

#### Search Operations
```
Ctrl+F (Cmd+F on macOS) to open search
Use filters and regex for advanced search
```

## ⚙️ Configuration

### Configuration File Location

**Windows**: `%APPDATA%\SyncPoint\config.json`
**macOS**: `~/Library/Application Support/SyncPoint/config.json`
**Linux**: `~/.config/SyncPoint/config.json`

### Basic Configuration Template

```json
{
  "general": {
    "theme": "dark",
    "language": "en",
    "startupDirectory": "/home/user/Documents"
  },
  "appearance": {
    "showHiddenFiles": false,
    "showFileExtensions": true,
    "defaultViewMode": "list"
  },
  "sync": {
    "autoSync": true,
    "syncInterval": 5000,
    "conflictResolution": "ask"
  },
  "security": {
    "enableEncryption": false,
    "encryptionAlgorithm": "AES-256"
  },
  "cloud": {
    "provider": "none",
    "autoUpload": false
  }
}
```

### Configuration Options

| Setting | Type | Default | Description |
|---------|------|---------|-------------|
| `theme` | string | `dark` | UI theme (light/dark) |
| `autoSync` | boolean | `true` | Enable automatic synchronization |
| `syncInterval` | number | `5000` | Sync check interval (ms) |
| `showHiddenFiles` | boolean | `false` | Display hidden files |
| `enableEncryption` | boolean | `false` | Enable file encryption |

## 💻 Usage

### Common Workflows

#### Synchronizing Directories
1. Navigate to source directory in left pane
2. Navigate to destination in right pane
3. Click "Sync" from Tools menu
4. Select sync mode (One-way / Bi-directional)
5. Review changes and confirm

#### Batch Renaming Files
1. Select multiple files (Ctrl+Click)
2. Right-click → Batch Rename
3. Enter naming pattern (e.g., `Photo_{number}.jpg`)
4. Preview results and apply

#### Advanced Search
1. Press Ctrl+F to open search panel
2. Enter search term
3. Add filters (file type, date range, size)
4. Use regex for complex patterns
5. Results update in real-time

#### Managing Archives
1. Right-click archive file
2. Select "Extract Here" or "Extract To..."
3. For creating archives:
   - Select files
   - Right-click → "Create Archive"
   - Choose format and compression level

## 🤝 Contributing

We welcome contributions from the community! Whether you're fixing bugs, adding features, or improving documentation, your help makes SyncPoint better.

### Getting Started with Development

1. **Fork the Repository**
   ```bash
   Click "Fork" button on GitHub
   ```

2. **Clone Your Fork**
   ```bash
   git clone https://github.com/YOUR_USERNAME/SyncPoint.git
   cd SyncPoint
   ```

3. **Create a Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   # or for bug fixes
   git checkout -b fix/issue-description
   ```

4. **Install Development Dependencies**
   ```bash
   npm install
   npm run dev
   ```

### Development Workflow

1. **Make Your Changes**
   - Follow the code style guidelines (see below)
   - Write clear, descriptive commit messages
   - Add tests for new functionality

2. **Run Tests and Linting**
   ```bash
   npm run lint          # Run ESLint
   npm run format        # Format with Prettier
   npm test              # Run test suite
   npm run test:coverage # Generate coverage report
   ```

3. **Commit and Push**
   ```bash
   git add .
   git commit -m "feat: add new feature description"
   git push origin feature/your-feature-name
   ```

4. **Create a Pull Request**
   - Go to GitHub and create a pull request
   - Provide a clear description of your changes
   - Link any related issues
   - Wait for review and feedback

### Code Style Guidelines

#### TypeScript/JavaScript
- Use meaningful variable and function names
- Follow camelCase for variables and functions
- Use PascalCase for classes and components
- Keep functions small and focused
- Add JSDoc comments for public APIs

```typescript
/**
 * Synchronizes files between source and destination directories
 * @param source - Source directory path
 * @param destination - Destination directory path
 * @param options - Sync configuration options
 * @returns Promise<SyncResult>
 */
export async function syncDirectories(
  source: string,
  destination: string,
  options: SyncOptions
): Promise<SyncResult> {
  // Implementation
}
```

#### React Components
- Use functional components with hooks
- Keep components focused on single responsibility
- Use descriptive prop names
- Add PropTypes or TypeScript interfaces

```typescript
interface FileListProps {
  files: FileInfo[];
  onSelect: (file: FileInfo) => void;
  isLoading?: boolean;
}

const FileList: React.FC<FileListProps> = ({
  files,
  onSelect,
  isLoading = false,
}) => {
  // Component implementation
};
```

### Branch Naming Convention

- `feature/description` - New features
- `fix/issue-name` - Bug fixes
- `docs/topic` - Documentation updates
- `refactor/component-name` - Code refactoring
- `test/feature-name` - Test additions

### Commit Message Format

Follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, semicolons)
- `refactor`: Code refactoring
- `perf`: Performance improvements
- `test`: Adding or updating tests
- `chore`: Build, CI/CD, or dependency updates

**Example:**
```
feat(sync): implement bi-directional file synchronization

Add support for bi-directional sync with conflict resolution
strategies. Implement file tracking and change detection.

Closes #123
```

### Testing Requirements

- Write tests for all new features
- Maintain minimum 80% code coverage
- Use Jest for unit tests
- Use React Testing Library for component tests
- Run tests before submitting PR

```bash
# Run specific test
npm test -- --testPathPattern=sync

# Run with coverage
npm run test:coverage

# Watch mode during development
npm test -- --watch
```

### Pull Request Checklist

Before submitting your PR, ensure:

- [ ] Code follows style guidelines
- [ ] All tests pass (`npm test`)
- [ ] Linting passes (`npm run lint`)
- [ ] Coverage is maintained or improved
- [ ] Documentation is updated if needed
- [ ] Commit messages follow convention
- [ ] Branch is up-to-date with main
- [ ] No merge conflicts
- [ ] PR description is clear and complete

### Issue Guidelines

When reporting issues:
- Use clear, descriptive titles
- Include step-by-step reproduction
- Provide system information (OS, Node version)
- Include screenshots if applicable
- Attach relevant error logs

**Issue Template:**
```markdown
**Description**
Clear description of the issue

**Steps to Reproduce**
1. Step one
2. Step two
3. Step three

**Expected Behavior**
What should happen

**Actual Behavior**
What actually happens

**System Info**
- OS: [e.g., Windows 10]
- Node: [e.g., v18.0.0]
- SyncPoint: [e.g., v1.0.0]

**Screenshots**
[If applicable]
```

## 📋 Code of Conduct

### Our Commitment

We are committed to providing a welcoming and inclusive environment for all contributors and users.

### Expected Behavior

- Use welcoming and inclusive language
- Be respectful of differing opinions and experiences
- Accept constructive criticism gracefully
- Focus on what's best for the community
- Show empathy towards other community members

### Unacceptable Behavior

- Harassment or discrimination
- Insulting or derogatory comments
- Threats or violent language
- Unwelcome advances
- Spam or promotional content

### Reporting Issues

If you experience or witness unacceptable behavior, please report it to the maintainers at [conduct@syncpoint.dev](mailto:conduct@syncpoint.dev).

## 📄 License

SyncPoint is released under the **MIT License**. See the [LICENSE](LICENSE) file for full details.

```
MIT License

Copyright (c) 2026 AkiUse306

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

## 🆘 Support

### Getting Help

- **Documentation**: [docs/](docs/) directory
- **FAQ**: [FAQ.md](docs/FAQ.md)
- **Issues**: [GitHub Issues](https://github.com/AkiUse306/SyncPoint/issues)
- **Discussions**: [GitHub Discussions](https://github.com/AkiUse306/SyncPoint/discussions)
- **Email**: [support@syncpoint.dev](mailto:support@syncpoint.dev)

### Reporting Bugs

Found a bug? Please open an issue on GitHub with:
- Clear description
- Steps to reproduce
- Expected vs actual behavior
- System information
- Screenshots if applicable

### Feature Requests

Have an idea? Check existing [Issues](https://github.com/AkiUse306/SyncPoint/issues) and [Discussions](https://github.com/AkiUse306/SyncPoint/discussions) before opening a new feature request.

## 📊 Project Statistics

- **Repository**: [AkiUse306/SyncPoint](https://github.com/AkiUse306/SyncPoint)
- **License**: MIT
- **Node Version**: 18.0.0+
- **Status**: Active Development

## 🙏 Acknowledgments

We thank all contributors, testers, and users who have helped make SyncPoint better!

---

**Made with ❤️ by the SyncPoint Community**

Last Updated: 2026-01-06 08:41:24 UTC
