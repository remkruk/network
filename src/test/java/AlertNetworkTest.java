import org.example.AlertNetwork;
import org.example.AlertNetworkBFSImpl;
import org.example.AlertNetworkDFSImpl;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class AlertNetworkTest {

    private AlertNetwork alertNetwork;

    /*
    findAlertPropagationPath("A", "C") might return ["A", "B", "C"] (or ["A", "D", "C"])
     */
    @Test
    public void findAlertPropagationPathBFSScenario1() {
        // given
        alertNetwork = new AlertNetworkBFSImpl();
        alertNetwork.addDependency("A", "B");
        alertNetwork.addDependency("B", "C");
        alertNetwork.addDependency("A", "D");
        alertNetwork.addDependency("D", "C");

        // when
        var result = alertNetwork.findAlertPropagationPath("A", "C");

        // then
        assertThat(result).containsExactly("A", "B", "C");
    }

    @Test
    public void findAlertPropagationPathBFSScenario2() {
        // given
        alertNetwork = new AlertNetworkBFSImpl();
        alertNetwork.addDependency("A", "B");
        alertNetwork.addDependency("A", "C");
        alertNetwork.addDependency("B", "D");
        alertNetwork.addDependency("C", "E");
        alertNetwork.addDependency("D", "F");
        alertNetwork.addDependency("E", "F");
        alertNetwork.addDependency("F", "G");
        alertNetwork.addDependency("G", "H");
        alertNetwork.addDependency("H", "I");
        alertNetwork.addDependency("I", "J");
        alertNetwork.addDependency("D", "K");
        alertNetwork.addDependency("K", "J");

        // when
        var result = alertNetwork.findAlertPropagationPath("A", "J");

        // then
        assertThat(result).containsExactly("A", "B", "D", "K", "J");
    }

    /*
    getAffectedServices("A") would return ["B", "C", "D"]
     */
    @Test
    public void getAffectedServicesBFS() {
        // given
        alertNetwork = new AlertNetworkBFSImpl();
        alertNetwork.addDependency("A", "B");
        alertNetwork.addDependency("B", "C");
        alertNetwork.addDependency("A", "D");
        alertNetwork.addDependency("D", "C");

        // when
        var result = alertNetwork.getAffectedServices("A");

        //then
        assertThat(result).containsExactlyInAnyOrder("B", "C", "D");
    }

    /*
    findAlertPropagationPath("A", "C") might return ["A", "B", "C"] (or ["A", "D", "C"])
    */
    @Test
    public void findAlertPropagationPathDFSScenario1() {
        // given
        alertNetwork = new AlertNetworkDFSImpl();
        alertNetwork.addDependency("A", "B");
        alertNetwork.addDependency("B", "C");
        alertNetwork.addDependency("A", "D");
        alertNetwork.addDependency("D", "C");

        // when
        var result = alertNetwork.findAlertPropagationPath("A", "C");

        // then
        assertThat(result).containsExactly("A", "B", "C");
    }

    @Test
    public void findAlertPropagationPathDFSScenario2() {
        // given
        alertNetwork = new AlertNetworkDFSImpl();
        alertNetwork.addDependency("A", "B");
        alertNetwork.addDependency("A", "C");
        alertNetwork.addDependency("B", "D");
        alertNetwork.addDependency("C", "E");
        alertNetwork.addDependency("D", "F");
        alertNetwork.addDependency("E", "F");
        alertNetwork.addDependency("F", "G");
        alertNetwork.addDependency("G", "H");
        alertNetwork.addDependency("H", "I");
        alertNetwork.addDependency("I", "J");
        alertNetwork.addDependency("D", "K");
        alertNetwork.addDependency("K", "J");

        // when
        var result = alertNetwork.findAlertPropagationPath("A", "J");

        // then
        assertThat(result).containsExactly("A", "B", "D", "F", "G", "H", "I", "J");
    }

    /*
    getAffectedServices("A") would return ["B", "C", "D"]
    */
    @Test
    public void getAffectedServicesDFS() {
        // given
        alertNetwork = new AlertNetworkDFSImpl();
        alertNetwork.addDependency("A", "B");
        alertNetwork.addDependency("B", "C");
        alertNetwork.addDependency("A", "D");
        alertNetwork.addDependency("D", "C");

        // when
        var result = alertNetwork.getAffectedServices("A");

        //then
        assertThat(result).containsExactlyInAnyOrder("B", "C", "D");
    }
}
