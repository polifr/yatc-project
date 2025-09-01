# yatc-dependencytrack

Modulo che permette di installare nel cluster un'istanza bundle di [OWASP Dependency Track](https://owasp.org/www-project-dependency-track/).
Il servizio e' accessibile dall'esterno con il forward da parte dell'ingress, tramite l'url http://yatc-dependencytrack.

Variabili di ambiente configurabili: https://docs.dependencytrack.org/getting-started/deploy-docker/

## TODO
- Integrazione con l'istanza di Postgres per la persistenza dei dati (ref. https://docs.dependencytrack.org/getting-started/database-support/)
- Utilizzare le metriche esposte (ref. https://docs.dependencytrack.org/getting-started/monitoring/)
