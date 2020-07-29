# PbmYaml
A small class that provides an easy way to save data and keep comments in Yaml format using YamlSnake.

**This class was initially for the management of configuration file for Minecraft plugins under Spigot/Bukkit**

### Features

- Get values from a Yaml file.
- Change these values.
- Save these values on the disk in Yaml format.
- Save the comments present in the Yaml file.
- Be the same as bukkit but keeping the comments ;)

------------
### Fonctionnalités

- Obtenir des valeurs depuis un fichier Yaml.
- Changer ces valeurs.
- Enregistrer ces valeurs sur le dique au format Yaml.
- Sauvegarder les commentaires présent dans le fichier Yaml.
- Soit la même chose que bukkit mais en gardant les commentaire ;)


## How to use

Maven:

```XML
<repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
</repository>
```

```XML
<dependency>
        <groupId>com.github.Medialo</groupId>
        <artifactId>PbmYaml</artifactId>
        <version>VERSION</version>
</dependency>
```

## Example 

```JAVA
        File filetest = new File(this.getDataFolder(),"test.yml");
        PbmYaml fileConfiguration = new PbmYaml();
        fileConfiguration.load(filetest);
        fileConfiguration.set("parameter.value",false);
        fileConfiguration.save();
```  
