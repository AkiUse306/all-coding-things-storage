const { contextBridge, ipcRenderer } = require('electron');

contextBridge.exposeInMainWorld('electronAPI', {
  // Window controls
  minimizeWindow: () => ipcRenderer.invoke('window:minimize'),
  maximizeWindow: () => ipcRenderer.invoke('window:maximize'),
  closeWindow: () => ipcRenderer.invoke('window:close'),
  
  // Navigation
  navigate: (url) => ipcRenderer.invoke('browser:navigate', url),
  
  // DevTools
  toggleDevTools: () => ipcRenderer.invoke('devtools:toggle'),
  
  // Logging
  log: (message) => ipcRenderer.send('log:info', message),
  logError: (message) => ipcRenderer.send('log:error', message),
});
