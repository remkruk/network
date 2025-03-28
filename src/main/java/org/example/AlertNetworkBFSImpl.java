package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.lang3.tuple.Pair;

public class AlertNetworkBFSImpl implements AlertNetwork {

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

        Queue<String> queue = new LinkedList<>();
        Map<String, String> previousNode = new HashMap<>();
        List<String> visited = new ArrayList<>();

        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(target)) {
                return retrievePath(previousNode, target);
            }

            for (String neighbor : adjacencyMap.getOrDefault(current, List.of())) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    previousNode.put(neighbor, current);
                }
            }
        }

        return List.of();
    }

    @Override
    public List<String> getAffectedServices(String source) {
        if (!adjacencyMap.containsKey(source)) {
            return List.of();
        }

        Queue<String> queue = new LinkedList<>();
        List<String> visited = new ArrayList<>();

        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (String neighbor : adjacencyMap.getOrDefault(current, List.of())) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        visited.remove(source);

        return visited;
    }

    @Override
    public List<Pair<String, String>> suggestContainmentEdges(String source) {
        return List.of();
    }

    private List<String> retrievePath(Map<String, String> parent, String target) {
        List<String> path = new ArrayList<>();
        String current = target;

        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }

        return path.reversed();
    }
}
