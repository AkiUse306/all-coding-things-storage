# Contributing to Akincraft

Thank you for contributing! Akincraft is a dual-stack voxel engine project with Java and C++ prototype implementations.

## Guidelines

- Keep subsystems modular and separated by layer.
- Write engine code in the appropriate stack (`java/` or `cpp/`).
- Maintain clean, self-documenting code.
- Prefer explicit types and clear ownership semantics.
- Add small, focused tests or validation scenes where appropriate.

## Workflow

1. Fork the repository.
2. Create a feature branch.
3. Build and test locally in the stack you modify.
4. Submit a pull request with a clear summary and implementation details.

## Code Style

- Java: follow standard Java conventions and keep classes focused.
- C++: use RAII, avoid raw pointers where possible, and keep headers minimal.
- Keep rendering, physics, world logic, and gameplay systems decoupled.

## Issues

Open an issue for bugs, performance regressions, or architecture proposals.
