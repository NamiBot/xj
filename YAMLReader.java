public class YAMLReader {
    public static void main(String[] args) {
        System.out.println(String.format("%s,%s,%s,%s,%s,%s,%s", "PlayerName", "Base.X", "Base.y", "Base.z", "Town.x", "Town.y", "Town.z"));

        File folder = new File("/Users/namibot/thispc/tmp/xj/status");
        Stream.of(Optional.ofNullable(folder.listFiles()).orElseGet(() -> new File[] {}))
                .filter(Objects::nonNull)
                .filter(File::isFile)
                .filter(f -> f.getName().endsWith(".yml"))
                .filter(f -> !f.getName().toLowerCase().contains("_op"))
                .map(Lang.wrapFunction(f -> {
                    YamlConfiguration configuration = new YamlConfiguration();
                    configuration.load(f);
                    configuration.set("player.name", f.getName().substring(0, f.getName().indexOf('.')));
                    return configuration;
                }))
                .filter(y -> y.getInt("status") == 4)
                .filter(y -> Objects.nonNull(y.getString("locations.base-location.x")))
                .filter(y -> Objects.nonNull(y.getString("locations.town-cmdlocation.x")))
                .sorted(Comparator.comparing(a -> a.getString("player.name")))
                .map(y -> String.format("%s,%s,%s,%s,%s,%s,%s",
                        y.getString("player.name"),
                        y.getString("locations.base-location.x"),
                        y.getString("locations.base-location.y"),
                        y.getString("locations.base-location.z"),
                        y.getString("locations.town-cmdlocation.x"),
                        y.getString("locations.town-cmdlocation.y"),
                        y.getString("locations.town-cmdlocation.z")
                ))
                .forEach(System.out::println);
    }
}

