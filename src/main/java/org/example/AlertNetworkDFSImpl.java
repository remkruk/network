package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public class AlertNetworkDFSImpl implements AlertNetwork {

    private final Map<String, List<String>> adjacencyMap = new HashMap<>();

    @Override
    public void addService(String service) {
        adjacencyMap.putIfAbsent(service, new ArrayList<>());
    }

    @Override
    public void addDependency(String fromService, String toService) {
        addService(fromService);
        addService(toService);
        adjacencyMap.get(fromService).add(toService);
    }

    @Override
    public List<String> getDependencies(String service) {
        return adjacencyMap.getOrDefault(service, List.of());
    }

    @Override
    public List<String> findAlertPropagationPath(String source, String target) {
        // if some service is missing in graph
        if (!adjacencyMap.containsKey(source) || !adjacencyMap.containsKey(target)) {
            return List.of();
        }

        // if there's a direct connection
        if (adjacencyMap.get(source).contains(target)) {
            return List.of(source, target);
        }

        List<String> visited = new ArrayList<>();
        List<String> path = new ArrayList<>();

        if (dfs(source, target, visited, path)) {
            return path;
        }

        return List.of();
    }

    @Override
    public List<String> getAffectedServices(String source) {
        if (!adjacencyMap.containsKey(source)) {
            return List.of();
        }

        List<String> visited = new ArrayList<>();
        List<String> path = new ArrayList<>();

        // null is passed as target to go through all accessible nodes
        if (dfs(source, null, visited, path)) {
            visited.removeFirst();
            return visited;
        }

        return List.of();
    }

    @Override
    public List<Pair<String, String>> suggestContainmentEdges(String source) {
        return List.of();
    }

    private boolean dfs(String current, String target, List<String> visited, List<String> path) {
        visited.add(current);
        path.add(current);

        if (current.equals(target)) {
            return true;
        }

        // we've visited all nodes
        if (visited.size() == adjacencyMap.size()) {
            return true;
        }

        for (String neighbour : adjacencyMap.get(current)) {
            if (!visited.contains(neighbour)) {
                if (dfs(neighbour, target, visited, path)) {
                    return true;
                }
            }
        }

        path.removeLast(); // backtracking
        return false;
    }
}
