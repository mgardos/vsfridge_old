package ar.net.mgardos;

import java.util.List;

public interface SmartFridgeInspector {
    List<Snapshot> survey(List<Product> products);
}
