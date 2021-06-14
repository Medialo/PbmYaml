package fr.medialo.api.pbmyaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PbmYaml extends DataFile {

    private static final transient String EXTENSION = "yml";
    private final Yaml yaml;

    public PbmYaml() {
        DumperOptions yamlOptions = new DumperOptions();
        yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yamlOptions.setPrettyFlow(true);
        this.yaml = new Yaml(yamlOptions);
    }

    @Override
    public final void load(final Path path) {
        load0(path);
        try {
            this.data = new PbmMap(yaml.load(Files.newBufferedReader(path)));
            this.path = path;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void save(final KeepComments keepComments) {
        try {
            if (KeepComments.YES.equals(keepComments)) {
                final Pattern pattern = Pattern.compile("^[^:\\n]*:");
                Matcher matcher;
                List<String> comments = new LinkedList<>();
                final Map<String, List<String>> commentsMap = new HashMap<>();
                final List<String> lines = Files.readAllLines(this.path);
                for (String line : lines) {
                    matcher = pattern.matcher(line);
                    if (line.startsWith("#")) {
                        comments.add(line);
                    } else if (!comments.isEmpty() && matcher.find()) {
                        commentsMap.put(matcher.group(), comments);
                        comments = new LinkedList<>();
                    }
                }
                final List<String> finalYaml = new LinkedList<>(Arrays.asList(this.yaml.dump(this.data.getData()).split("\n")));
                for (int i = 0; i < finalYaml.size(); i++) {
                    matcher = pattern.matcher(finalYaml.get(i));
                    if (matcher.find()) {
                        final String key = matcher.group();
                        if (commentsMap.containsKey(key)) {
                            finalYaml.addAll(i, commentsMap.get(key));
                            i += commentsMap.get(key).size();
                        }
                    }
                }
                Files.write(this.path, finalYaml);
            } else {
                Files.write(this.path, Collections.singleton(this.yaml.dump(this.data.getData())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getExtension() {
        return PbmYaml.EXTENSION;
    }
}
