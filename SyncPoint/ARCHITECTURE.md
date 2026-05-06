# SyncPoint Architecture Documentation

## Overview

SyncPoint is a cross-platform application designed to provide seamless file access and synchronization across different operating systems and environments. This document outlines the architecture, component organization, and design patterns used throughout the application.

---

## Table of Contents

1. [Project Structure](#project-structure)
2. [Core Architectural Layers](#core-architectural-layers)
3. [Cross-Platform File Access](#cross-platform-file-access)
4. [Theming System](#theming-system)
5. [Component Organization](#component-organization)
6. [Data Flow](#data-flow)
7. [Dependency Injection](#dependency-injection)
8. [Error Handling](#error-handling)

---

## Project Structure

```
SyncPoint/
├── src/
│   ├── core/                      # Core application logic
│   │   ├── file-access/           # Cross-platform file operations
│   │   ├── sync/                  # Synchronization engine
│   │   └── utils/                 # Utility functions
│   ├── presentation/
│   │   ├── components/            # UI Components
│   │   ├── pages/                 # Page components
│   │   ├── layouts/               # Layout components
│   │   └── hooks/                 # React hooks
│   ├── theme/                     # Theming system
│   │   ├── themes/                # Theme definitions
│   │   ├── colors/                # Color palettes
│   │   └── providers/             # Theme providers
│   ├── services/                  # Business logic services
│   │   ├── file-service.ts
│   │   ├── sync-service.ts
│   │   └── settings-service.ts
│   ├── store/                     # State management
│   │   ├── reducers/
│   │   ├── actions/
│   │   └── selectors/
│   ├── types/                     # TypeScript type definitions
│   └── App.tsx                    # Application root
├── tests/
│   ├── unit/
│   ├── integration/
│   └── e2e/
├── config/                        # Configuration files
├── docs/                          # Additional documentation
└── public/                        # Static assets
```

---

## Core Architectural Layers

### 1. **Presentation Layer**
Handles all UI concerns, including components, pages, and user interactions.

- **Components**: Reusable UI building blocks (buttons, modals, lists)
- **Pages**: Full-page components representing routes
- **Layouts**: Wrapper components for consistent page structure
- **Hooks**: Custom React hooks for shared logic

### 2. **Business Logic Layer**
Contains services that implement core functionality.

- **FileService**: Handles file operations across platforms
- **SyncService**: Manages file synchronization
- **SettingsService**: Manages application settings and preferences

### 3. **Data Access Layer**
Abstracts access to data sources and platforms.

- **CrossPlatformFileAccess**: Platform-specific implementations
- **StorageAdapters**: Different storage backend implementations
- **APIClient**: Handles remote synchronization requests

### 4. **State Management Layer**
Uses Redux/Context API for global application state.

- **Store**: Centralized state container
- **Reducers**: State update logic
- **Actions**: Dispatched events
- **Selectors**: State queries

---

## Cross-Platform File Access

### Architecture

The cross-platform file access system is built on an abstraction layer that provides a unified interface for different operating systems.

```
┌─────────────────────────────────────────┐
│   Application Layer (File Service)      │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│  Platform Abstraction Interface         │
│  (IFileAccessProvider)                  │
└──────────────────┬──────────────────────┘
                   │
    ┌──────────────┼──────────────┐
    │              │              │
┌───▼─────┐   ┌───▼─────┐   ┌───▼─────┐
│ Windows │   │  macOS  │   │  Linux  │
└────┬────┘   └────┬────┘   └────┬────┘
     │             │             │
┌────▼─────────────▼─────────────▼────┐
│   Operating System APIs              │
└─────────────────────────────────────┘
```

### Key Components

#### IFileAccessProvider Interface
```typescript
interface IFileAccessProvider {
  // File operations
  readFile(path: string): Promise<Buffer>;
  writeFile(path: string, data: Buffer): Promise<void>;
  deleteFile(path: string): Promise<void>;
  listDirectory(path: string): Promise<FileInfo[]>;
  
  // Metadata operations
  getFileStats(path: string): Promise<FileStats>;
  setFilePermissions(path: string, permissions: number): Promise<void>;
  
  // Path operations
  resolvePath(relativePath: string): Promise<string>;
  getNormalizedPath(path: string): string;
}
```

#### Platform-Specific Implementations

- **WindowsFileAccess**: Handles Windows-specific file operations using Node.js fs module with Windows path conventions
- **UnixFileAccess**: Handles macOS/Linux file operations with POSIX-compliant paths
- **WebFileAccess**: Handles browser-based file operations using File API

#### Platform Detection & Selection

```typescript
class FileAccessFactory {
  static createProvider(): IFileAccessProvider {
    if (process.platform === 'win32') {
      return new WindowsFileAccess();
    } else if (process.platform === 'darwin') {
      return new MacOSFileAccess();
    } else {
      return new LinuxFileAccess();
    }
  }
}
```

### File Operation Features

- **Atomic Operations**: Ensures file operations complete fully or roll back
- **Permission Handling**: Cross-platform permission mapping
- **Encoding Support**: UTF-8 and binary file handling
- **Symbolic Links**: Follows or ignores based on configuration
- **Large File Support**: Streaming for files larger than memory
- **Error Recovery**: Retry logic with exponential backoff

---

## Theming System

### Architecture Overview

The theming system provides dynamic, runtime-switchable themes with support for light, dark, and custom modes.

```
┌─────────────────────────────────┐
│   Application Root              │
└────────────┬────────────────────┘
             │
┌────────────▼────────────────────┐
│   ThemeProvider Component        │
└────────────┬────────────────────┘
             │
┌────────────▼────────────────────┐
│   useTheme Hook                 │
│   - Current Theme               │
│   - Theme Switching             │
│   - Theme Persistence           │
└────────────┬────────────────────┘
             │
    ┌────────┴────────┐
    │                 │
┌───▼─────────┐  ┌───▼─────────┐
│ Light Theme │  │ Dark Theme  │
└────┬────────┘  └────┬────────┘
     │                │
     └────────┬───────┘
              │
        ┌─────▼──────┐
        │ CSS-in-JS  │
        │  Styled    │
        │ Components │
        └────────────┘
```

### Theme Structure

```typescript
interface Theme {
  name: string;
  mode: 'light' | 'dark' | 'custom';
  colors: {
    primary: string;
    secondary: string;
    accent: string;
    background: string;
    surface: string;
    text: {
      primary: string;
      secondary: string;
      disabled: string;
    };
    status: {
      success: string;
      warning: string;
      error: string;
      info: string;
    };
  };
  typography: {
    fontFamily: string;
    sizes: {
      xs: string;
      sm: string;
      md: string;
      lg: string;
      xl: string;
    };
    weights: {
      light: number;
      regular: number;
      semibold: number;
      bold: number;
    };
  };
  spacing: {
    xs: string;
    sm: string;
    md: string;
    lg: string;
    xl: string;
  };
  borderRadius: {
    sm: string;
    md: string;
    lg: string;
    full: string;
  };
  shadows: {
    sm: string;
    md: string;
    lg: string;
  };
  transitions: {
    fast: string;
    normal: string;
    slow: string;
  };
}
```

### Theme Provider

```typescript
<ThemeProvider defaultTheme="light" storageKey="syncpoint-theme">
  <App />
</ThemeProvider>
```

### Using Themes in Components

```typescript
const MyComponent = styled.div(({ theme }: { theme: Theme }) => ({
  backgroundColor: theme.colors.background,
  color: theme.colors.text.primary,
  padding: theme.spacing.md,
  borderRadius: theme.borderRadius.md,
  transition: theme.transitions.normal,
}));
```

### Theme Customization

Users can create custom themes by:
1. Extending base theme
2. Overriding specific color values
3. Adjusting typography and spacing
4. Saving custom themes to local storage

### Predefined Themes

- **Light Theme**: High contrast, optimized for daytime use
- **Dark Theme**: Low brightness, optimized for nighttime use
- **High Contrast Theme**: Enhanced accessibility for visually impaired users

---

## Component Organization

### Component Hierarchy

#### Smart Components (Containers)
Connected to state management, handle business logic.

```
FileListContainer
├── Connected to Redux/Context
├── Handles data fetching
├── Manages local state
└── Passes data to presentational components
```

#### Presentational Components (Dumb)
Pure components, receive data via props, no side effects.

```
FileList (Presentational)
├── Receives: files[], isLoading, error
├── Renders: UI based on props
├── Emits: onClick, onDelete, etc.
└── No side effects
```

### Component Structure

```
components/
├── Common/
│   ├── Button.tsx
│   ├── Modal.tsx
│   ├── Spinner.tsx
│   └── Toast.tsx
├── FileExplorer/
│   ├── FileList.tsx
│   ├── FileItem.tsx
│   ├── FileSearch.tsx
│   └── FileContextMenu.tsx
├── Sync/
│   ├── SyncStatus.tsx
│   ├── SyncProgress.tsx
│   └── SyncHistory.tsx
├── Settings/
│   ├── ThemeSelector.tsx
│   ├── SyncSettings.tsx
│   └── GeneralSettings.tsx
└── Layout/
    ├── Header.tsx
    ├── Sidebar.tsx
    └── MainLayout.tsx
```

### Component Best Practices

1. **Single Responsibility**: Each component does one thing well
2. **Props Interface**: Clear, documented prop types
3. **Error Boundaries**: Wrapped critical sections
4. **Memo Optimization**: Prevent unnecessary re-renders
5. **Accessibility**: ARIA labels, keyboard navigation
6. **Testing**: Unit and integration tests

---

## Data Flow

### Redux State Management Flow

```
User Action (UI)
    ↓
Dispatch Action
    ↓
Reducer Updates State
    ↓
Selectors Compute Derived State
    ↓
Components Re-render
    ↓
Side Effects (Sagas/Thunks)
    ↓
API Calls / File Operations
    ↓
Dispatch Result Action
    ↓
State Updates
```

### Example: File Upload Flow

```
1. User clicks "Upload" button
   ↓
2. FileUploadModal component dispatches uploadFile action
   ↓
3. Upload thunk middleware intercepts
   ↓
4. FileService.uploadFile() called
   ↓
5. Cross-platform file handler processes file
   ↓
6. Response dispatched as uploadSuccess action
   ↓
7. Reducer updates files state
   ↓
8. FileList component re-renders with new file
   ↓
9. Toast notification displayed
```

---

## Dependency Injection

### Service Injection Pattern

```typescript
// Service definition
class FileService {
  constructor(
    private fileAccessProvider: IFileAccessProvider,
    private syncService: SyncService,
    private logger: Logger
  ) {}
}

// Injection in application root
const fileAccessProvider = FileAccessFactory.createProvider();
const syncService = new SyncService(fileAccessProvider);
const fileService = new FileService(
  fileAccessProvider,
  syncService,
  logger
);

// Provide to application
const services = {
  fileService,
  syncService,
};

<ServicesContext.Provider value={services}>
  <App />
</ServicesContext.Provider>
```

### Hook-based Service Access

```typescript
function useFileService() {
  return useContext(ServicesContext).fileService;
}

// Usage in components
const MyComponent = () => {
  const fileService = useFileService();
  // Use fileService
};
```

---

## Error Handling

### Error Hierarchy

```typescript
abstract class SyncPointError extends Error {
  constructor(
    message: string,
    public code: string,
    public severity: 'low' | 'medium' | 'high'
  ) {
    super(message);
  }
}

class FileAccessError extends SyncPointError {}
class SyncError extends SyncPointError {}
class PermissionError extends SyncPointError {}
class NetworkError extends SyncPointError {}
```

### Error Handling Strategy

1. **Validation**: Input validation at component level
2. **Try-Catch**: Async operations wrapped in try-catch
3. **Error Boundaries**: React error boundaries for component errors
4. **Logging**: All errors logged with context
5. **User Feedback**: Toast notifications for user-facing errors
6. **Retry Logic**: Automatic retry for transient errors

### Global Error Handler

```typescript
class ErrorHandler {
  static handle(error: Error): void {
    if (error instanceof FileAccessError) {
      // Handle file access errors
      showToast(error.message, 'error');
    } else if (error instanceof NetworkError) {
      // Handle network errors with retry
      scheduleRetry();
    } else {
      // Log unexpected errors
      logger.error(error);
    }
  }
}
```

---

## Performance Considerations

### Optimization Strategies

1. **Code Splitting**: Route-based lazy loading
2. **Memoization**: useCallback, useMemo for expensive computations
3. **Virtual Scrolling**: For large file lists
4. **Debouncing**: Search and filter operations
5. **Caching**: File metadata and directory listings
6. **Chunked File Operations**: Large file streaming
7. **Web Workers**: Offload heavy computation

### Monitoring

- Error tracking and reporting
- Performance metrics collection
- User analytics for feature usage
- Sync operation metrics

---

## Security Considerations

1. **File Permissions**: Respect OS file permissions
2. **Path Traversal**: Prevent access outside designated directories
3. **Encryption**: Support for encrypted file storage
4. **Authentication**: Secure sync endpoint authentication
5. **Audit Logging**: Track file access and modifications
6. **Input Sanitization**: Validate all file paths and names

---

## Future Enhancements

- [ ] Real-time file watching with debounce
- [ ] Advanced conflict resolution strategies
- [ ] Plugin system for custom file handlers
- [ ] Bandwidth throttling for sync operations
- [ ] Advanced search with full-text indexing
- [ ] Collaborative features for shared access

---

## Contributing

When contributing to SyncPoint, please follow these architectural guidelines:

1. Maintain clear separation of concerns
2. Use the established patterns for new features
3. Add tests for new components and services
4. Update documentation for significant changes
5. Follow the component structure conventions

---

## References

- [React Documentation](https://react.dev)
- [Redux Documentation](https://redux.js.org)
- [TypeScript Handbook](https://www.typescriptlang.org/docs)
- [Node.js File System API](https://nodejs.org/api/fs.html)
