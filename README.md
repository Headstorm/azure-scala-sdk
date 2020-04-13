# azure-scala-sdk
Azure Scala SDK tooling

## Usage

## Development Setup

We use [Mill](https://github.com/lihaoyi/mill) as our build tool. See Mill's documentation
installation instructions.  

### Intellij Integration
You need to run `mill mill.scalalib.GenIdea/idea` to generate the required `.idea` files. 
This command needs to be rerun everytime the `build.sc` file is updated.

### Running Tests

```bash
mill storage.test 
```
