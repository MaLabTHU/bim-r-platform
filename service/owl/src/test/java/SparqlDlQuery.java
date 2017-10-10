import openllet.jena.PelletReasonerFactory;
import openllet.query.sparqldl.jena.SparqlDLExecutionFactory;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * Created by Luciferz2012 on 02/16/2017.
 */
public class SparqlDlQuery {

    // The ontology loaded as dataset
//    private static final String ontology = "data/university0-0.owl";
    private static final String ontology = SparqlDlQuery.class.getResource("university0-0.owl").getFile();
    private static final String[] queries = new String[]{
            // One of the original LUBM queries
//            "data/lubm-query4.sparql",
            SparqlDlQuery.class.getResource("lubm-query4.sparql").getFile(),
            // A SPARQL-DL query
//            "data/lubm-sparql-dl.sparql",
            SparqlDlQuery.class.getResource("lubm-sparql-dl.sparql").getFile(),
            // A SPARQL-DL with the SPARQL-DL extensions vocabulary
//            "data/lubm-sparql-dl-extvoc.sparql",
            SparqlDlQuery.class.getResource("lubm-sparql-dl-extvoc.sparql").getFile(),
    };

    public void run() {
        for (final String query : queries) {
            // First create a Jena ontology model backed by the Pellet reasoner
            // (note, the Pellet reasoner is required)
            final OntModel m = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);

            // Then read the _data from the file into the ontology model
            m.read(ontology);

            // Now read the query file into a query object
            final Query q = QueryFactory.read(query);

            // Create a SPARQL-DL query execution for the given query and
            // ontology model
            final QueryExecution qe = SparqlDLExecutionFactory.create(q, m);

            // We want to execute a SELECT query, do it, and return the result set
            final ResultSet rs = qe.execSelect();

            // Print the query for better understanding
            System.out.println(q.toString());

            // There are different things we can do with the result set, for
            // instance iterate over it and process the query solutions or, what we
            // do here, just print out the results
            ResultSetFormatter.out(rs);

            // And an empty line to make it pretty
            System.out.println();
        }
    }

    public static void main(final String[] args) {
        final SparqlDlQuery app = new SparqlDlQuery();
        app.run();
    }

}