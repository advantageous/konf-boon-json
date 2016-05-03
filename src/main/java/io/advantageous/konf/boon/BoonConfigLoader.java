package io.advantageous.konf.boon;

import io.advantageous.boon.json.JsonParserAndMapper;
import io.advantageous.boon.json.JsonParserFactory;
import io.advantageous.config.Config;
import io.advantageous.config.ConfigLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static io.advantageous.config.ResourceUtils.findResource;

public class BoonConfigLoader {

    /**
     * Alias for `load`. Loads a config file.
     *
     * @param resources classpath resources to from which to load javascript
     * @return Config.
     */
    public static Config boonConfig(final String... resources) {
        return loadLaxJson(resources);
    }

    /**
     * Loads a config files as LAX JSON
     * Allow comments (// /* #), extra comma, unquoted strings.
     *
     * @param resources classpath resources to from which to load javascript
     * @return Config.
     */
    public static Config loadLaxJson(final String... resources) {
        return  loadResources(new JsonParserFactory().lax().create(), resources);
    }


    /**
     * Loads a config files as Strict JSON.
     *
     * @param resources classpath resources to from which to load javascript
     * @return Config.
     */
    public static Config loadStrictJson(final String... resources) {
        return  loadResources(new JsonParserFactory().createFastObjectMapperParser(), resources);
    }


    private static Config loadResources(final JsonParserAndMapper mapper, final String[] resources) {
        final List<Config> configList = new ArrayList<>(resources.length);

        for (final String resource : resources) {
            try (final InputStream inputStream = findResource(resource)) {
                configList.add(ConfigLoader.loadFromObject(mapper.parseMap(inputStream)));
            } catch (final Exception e) {
                throw new IllegalArgumentException("unable to parse JSON. " + resource, e);
            }
        }

        return ConfigLoader.configs(configList.toArray(new Config[configList.size()]));
    }
}
