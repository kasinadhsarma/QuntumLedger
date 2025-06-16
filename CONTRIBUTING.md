# Contributing to QuantumLedger

First off, thank you for considering contributing to QuantumLedger! It's people like you that make QuantumLedger such a great tool.

## Code of Conduct

This project and everyone participating in it is governed by the QuantumLedger Code of Conduct. By participating, you are expected to uphold this code.

## How Can I Contribute?

### Reporting Bugs

This section guides you through submitting a bug report for QuantumLedger. Following these guidelines helps maintainers and the community understand your report, reproduce the behavior, and find related reports.

**Before Submitting A Bug Report**

* Check the documentation for a list of common questions and problems.
* Determine which repository the problem should be reported in.
* Perform a cursory search to see if the problem has already been reported.

**How Do I Submit A Good Bug Report?**

Bugs are tracked as GitHub issues. Create an issue and provide the following information:

* Use a clear and descriptive title
* Describe the exact steps which reproduce the problem
* Provide specific examples to demonstrate the steps
* Describe the behavior you observed after following the steps
* Explain which behavior you expected to see instead and why
* Include screenshots and animated GIFs if possible
* Include your environment details (OS, Java version, etc.)

### Suggesting Enhancements

This section guides you through submitting an enhancement suggestion for QuantumLedger.

**Before Submitting An Enhancement Suggestion**

* Check if there's already a feature providing your enhancement.
* Check the issues list for similar suggestions.

**How Do I Submit A Good Enhancement Suggestion?**

Enhancement suggestions are tracked as GitHub issues. Create an issue and provide the following information:

* Use a clear and descriptive title
* Provide a detailed description of the suggested enhancement
* Provide specific examples to demonstrate the steps
* Describe the current behavior and explain the behavior you expected to see
* Explain why this enhancement would be useful
* List some other applications where this enhancement exists

### Pull Requests

* Fill in the required template
* Do not include issue numbers in the PR title
* Include screenshots and animated GIFs in your pull request whenever possible
* Follow the Java styleguide
* Include unit tests when adding new features
* End all files with a newline
* Avoid platform-dependent code

## Styleguides

### Git Commit Messages

* Use the present tense ("Add feature" not "Added feature")
* Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
* Limit the first line to 72 characters or less
* Reference issues and pull requests liberally after the first line
* Consider starting the commit message with an applicable emoji:
    * 🎨 `:art:` when improving the format/structure of the code
    * 🐎 `:racehorse:` when improving performance
    * 🚱 `:non-potable_water:` when plugging memory leaks
    * 📝 `:memo:` when writing docs
    * 🐛 `:bug:` when fixing a bug
    * 🔥 `:fire:` when removing code or files
    * 💚 `:green_heart:` when fixing the CI build
    * ✅ `:white_check_mark:` when adding tests
    * 🔒 `:lock:` when dealing with security
    * ⬆️ `:arrow_up:` when upgrading dependencies
    * ⬇️ `:arrow_down:` when downgrading dependencies

### Java Styleguide

* Use 4 spaces for indentation
* Keep lines under 100 characters
* Follow standard Java naming conventions
* Document public APIs
* Include unit tests for new code
* Use meaningful variable and method names
* Keep methods short and focused
* Use appropriate design patterns
* Follow SOLID principles

### Documentation Styleguide

* Use [Markdown](https://guides.github.com/features/mastering-markdown/) for documentation
* Reference methods and classes using backticks
* Include code examples when appropriate
* Keep explanations clear and concise
* Use proper spelling and grammar
* Include diagrams when helpful
* Update documentation when changing code

## Setting up your development environment

1. Fork the repository
2. Clone your fork
3. Install prerequisites:
   * JDK 17
   * Maven 3.8+
   * PostgreSQL 12+
4. Set up the database:
   ```bash
   sudo service postgresql start
   sudo -u postgres psql -c "CREATE DATABASE quantumledger;"
   sudo -u postgres psql -c "ALTER USER postgres WITH PASSWORD 'postgres';"
   ```
5. Build the project:
   ```bash
   mvn clean install
   ```
6. Run the tests:
   ```bash
   mvn test
   ```

## Community

* Join our [Discord server](https://discord.gg/quantumledger)
* Follow us on [Twitter](https://twitter.com/quantumledger)
* Read our [blog](https://quantumledger.com/blog)

## Questions?

Feel free to contact the project maintainers:

* Email: support@quantumledger.com
* Discord: Maintainers are active on our Discord server
* GitHub Issues: For bugs and feature requests
