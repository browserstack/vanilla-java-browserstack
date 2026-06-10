# Vanilla Java (Selenium) with BrowserStack

Run Selenium WebDriver tests on [BrowserStack Automate](https://automate.browserstack.com/)
using the **BrowserStack Java SDK** — no test framework required. The SDK attaches as a
`-javaagent:`, reads `browserstack.yml`, and runs your tests across the configured platform
matrix with zero code changes for credentials, platforms, Test Observability, and Local.

This sample contains exactly two tests:

- **Sample test** (`BStackDemoTest`) — an add-to-cart flow on [bstackdemo.com](https://bstackdemo.com/).
- **Local test** (`BStackLocalTest`) — verifies the BrowserStack Local tunnel by opening
  `http://bs-local.com:45454` and asserting the page title.

## Prerequisites

- A [BrowserStack account](https://www.browserstack.com/users/sign_up) (username + access key).
- **JDK 11+** — Selenium 4.23 requires Java 11 (`maven.compiler.source/target = 11`).
- **Maven 3.6+**.

## Setup

1. Clone this repository and change into it:
   ```bash
   git clone https://github.com/browserstack/vanilla-java-browserstack.git
   cd vanilla-java-browserstack
   ```
2. Configure your BrowserStack credentials. Either edit `userName` / `accessKey` in
   `browserstack.yml`, or export them as environment variables (env vars take precedence):
   ```bash
   export BROWSERSTACK_USERNAME="YOUR_USERNAME"
   export BROWSERSTACK_ACCESS_KEY="YOUR_ACCESS_KEY"
   ```
3. Resolve dependencies (downloads Selenium and the BrowserStack Java SDK):
   ```bash
   mvn clean compile
   ```

The `-javaagent:` jar is wired automatically: the `maven-dependency-plugin` resolves the
SDK jar path into the `${com.browserstack:browserstack-java-sdk:jar}` property, and the
`exec-maven-plugin` passes it as `-javaagent:` when launching each test class.

## Run Sample Test

Runs the bstackdemo.com add-to-cart flow across the platforms in `browserstack.yml`:

```bash
mvn test
```

Or, equivalently, via the exec goal:

```bash
mvn compile exec:exec
```

## Run Local Test

`browserstackLocal: true` in `browserstack.yml` tells the SDK to start a BrowserStack Local
tunnel before the session, so the remote browser can reach `http://bs-local.com:45454`:

```bash
mvn compile exec:exec -P sample-local
```

To run both tests concurrently:

```bash
mvn compile exec:exec -P run-parallel
```

## Notes / Dashboard

- View runs, video, logs, and network traffic at
  [automate.browserstack.com](https://automate.browserstack.com/).
- With `testObservability: true`, the same build also appears in
  [Test Observability](https://observability.browserstack.com/).
- Platforms, parallelism, Local, Observability, and debugging flags are all controlled from
  `browserstack.yml` — no code changes needed to add browsers or devices.
- Vanilla Java has no test runner, so `browserstack.yml` intentionally omits the `framework:`
  field. The `-javaagent:` still instruments the Selenium `RemoteWebDriver` so sessions are
  created and reported. Add `framework:` only when adopting a supported test framework
  (TestNG / JUnit 4 / JUnit 5 / Cucumber).
