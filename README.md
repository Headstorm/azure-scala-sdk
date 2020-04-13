# azure-scala-sdk
Azure Scala SDK tooling

Azure Scala SDK is lovingly developed by Headstorm's Open Source group. Please reach out to us at: opensource@headstorm.com

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
