package test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericConfig implements Config {

    private String fileName;
    private List<Agent> agents;

    public void setConfFile(String name) {
        this.fileName = name;
    }

    @Override
    public void create() {
        agents = new ArrayList<>();
        Stream<String> s = null;
        try {
            s = Files.lines(Paths.get(fileName));
            List<String> lines = s.collect(Collectors.toList());
            if(lines.size() % 3 == 0) {
                for (int i = 0; i < lines.size(); i += 3) {
                    String className = lines.get(i).trim();
                    String[] subs = lines.get(i+1).split(",");
                    String[] pubs = lines.get(i+2).split(",");

                    Class<?> c = Class.forName(className);
                    if(Agent.class.isAssignableFrom(c)) {
                        Agent agent = (Agent) c.getConstructor(String[].class, String[].class)
                                .newInstance(subs, pubs);
                        Agent parallelAgent = new ParallelAgent(agent);
                        agents.add(parallelAgent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(s != null) {
                try {
                    s.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void close() {
        if(agents != null) {
            for(Agent agent : agents) {
                agent.close();
            }
        }
    }

    @Override
    public String getName() {
        return "Generic Config";
    }

    @Override
    public int getVersion() {
        return 1;
    }
}