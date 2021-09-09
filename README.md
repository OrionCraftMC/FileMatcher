# File Matcher

Tool used to match two different files/directories and outputs a match file that maps one side to the other.

## Example Output

```
/armor/chain_1.png->/assets/minecraft/textures/models/armor/chainmail_layer_1.png
/armor/chain_2.png->/assets/minecraft/textures/models/armor/chainmail_layer_2.png
/armor/cloth_1.png->/assets/minecraft/textures/models/armor/leather_layer_1.png
/armor/cloth_1_b.png->/assets/minecraft/textures/models/armor/leather_layer_1_overlay.png
/armor/cloth_2.png->/assets/minecraft/textures/models/armor/leather_layer_2.png
```

## Usage

```
Usage: FileMatcher [OPTIONS] PATH1 PATH2 OUTPUTMATCHPATH [BASEPATH1]
                   [BASEPATH2]

  Matches two different files/directories and outputs a match file that maps
  one side to the other

Options:
  -h, --help  Show this message and exit

Arguments:
  PATH1            The path for the first file/directory to compare with
  PATH2            The path for the second file/directory to compare with
  OUTPUTMATCHPATH  The output file with the file matches
  BASEPATH1        The root/base path for the first file/directory
  BASEPATH2        The root/base path for the second file/directory
```