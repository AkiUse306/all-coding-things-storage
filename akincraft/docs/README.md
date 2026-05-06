# Akincraft GitHub Pages

This directory contains the GitHub Pages documentation for the Akincraft project.

## Structure

- `index.html` - Main landing page with project overview and features
- `.nojekyll` - Tells GitHub Pages to serve static files without Jekyll processing

## Local Testing

To test the GitHub Pages locally, you can use Python's built-in server:

```bash
cd docs
python3 -m http.server 8000
```

Then open `http://localhost:8000` in your browser.

## Deployment

GitHub Pages is automatically deployed on every push to `main` via the `deploy-pages.yml` workflow.

## Customization

To customize the GitHub Pages site:
1. Edit `index.html` directly for content changes
2. Modify the embedded CSS for styling changes
3. Add additional HTML files as needed
4. Push to `main` branch to trigger automatic deployment

## Account Settings

To enable GitHub Pages for this repository:
1. Go to repository Settings
2. Navigate to Pages
3. Set Source to "GitHub Actions" 
4. The deployment workflow will handle the rest
