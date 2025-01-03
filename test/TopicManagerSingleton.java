package test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TopicManagerSingleton {


    public static class TopicManager{

        private static final TopicManager instance = new TopicManager();
        Map<String,Topic> topics;

        private TopicManager() {
            this.topics = new HashMap<>();

        }

        public synchronized Topic getTopic(String name)
        {
            if(!topics.containsKey(name))
            {
                topics.put(name, new Topic(name));

            }
            return topics.get(name);
        }

        public Collection<Topic> getTopics()
        {
            return topics.values();

        }

        public void clear()
        {
            topics.clear();

        }

    }

    public static TopicManager get() {
        return TopicManager.instance;
    }

    
}
