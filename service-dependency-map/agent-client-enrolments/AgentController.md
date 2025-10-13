# agent-client-enrolments

## AgentController

## `DELETE /enrolments-orchestrator/agents/:arn`

**Description:** De-enrol an agent and terminate their active sessions based on their Agent Reference Number (ARN).

### Sequence of Interactions

1. **API Call:** `POST /agent-status-change/terminate/:arn` to `agent-status-change` to request the termination of the agent's status.
2. **API Call:** `DELETE /enrolment-store-proxy/enrolment-store/enrolments/:enrolmentKey` to `enrolment-store-proxy` to delete the agent's enrolment.

### Sequence Diagram

```mermaid
sequenceDiagram
    autonumber
    participant Upstream
    participant agent-client-enrolments
    participant agent-status-change
    participant enrolment-store-proxy

    Upstream->>+agent-client-enrolments: DELETE /enrolments-orchestrator/agents/:arn
    agent-client-enrolments->>+agent-status-change: POST /agent-status-change/terminate/:arn
    agent-status-change-->>-agent-client-enrolments: 200 OK
    agent-client-enrolments->>+enrolment-store-proxy: DELETE /enrolment-store-proxy/enrolment-store/enrolments/:enrolmentKey
    enrolment-store-proxy-->>-agent-client-enrolments: 204 No Content
    agent-client-enrolments-->>-Upstream: 204 No Content
```

---

## `DELETE /relationships/:arn/service/:service/client/:clientIdType/:clientId`

**Description:** De-allocates an agent's client's enrolment from the agent's group, and deletes the relationship.

### Sequence of Interactions

1. **API Call:** `GET /enrolment-store-proxy/enrolment-store/enrolments/:enrolmentKey/groups` to `enrolment-store-proxy` to get principal group IDs for the client's enrolment.
2. **API Call:** `DELETE /enrolment-store-proxy/enrolment-store/groups/:groupId/enrolments/:enrolmentKey` to `enrolment-store-proxy` to delete the client's enrolment from their group (for each group ID found).
3. **API Call:** `GET /enrolment-store-proxy/enrolment-store/delegated-enrolments/:arn/group-id` to `enrolment-store-proxy` to get the group ID for the agent's ARN.
4. **API Call:** `DELETE /tax-enrolments/groups/:agentGroupId/enrolments/:enrolmentKey` to `tax-enrolments` to de-allocate the client's enrolment from the agent's group.
5. **API Call:** `DELETE /agent-client-relationships/agent/:arn/service/:service/client/:clientId` to `agent-client-relationships` to delete the agent-client relationship.

### Sequence Diagram

```mermaid
sequenceDiagram
    autonumber
    participant Upstream
    participant agent-client-enrolments
    participant enrolment-store-proxy
    participant tax-enrolments
    participant agent-client-relationships

    Upstream->>+agent-client-enrolments: DELETE /relationships/:arn/service/:service/client/:clientIdType/:clientId
    agent-client-enrolments->>+enrolment-store-proxy: GET /enrolment-store-proxy/enrolment-store/enrolments/:enrolmentKey/groups
    enrolment-store-proxy-->>-agent-client-enrolments: 200 OK (returns group IDs)
    loop for each groupId
        agent-client-enrolments->>+enrolment-store-proxy: DELETE /enrolment-store-proxy/enrolment-store/groups/:groupId/enrolments/:enrolmentKey
        enrolment-store-proxy-->>-agent-client-enrolments: 204 No Content
    end
    agent-client-enrolments->>+enrolment-store-proxy: GET /enrolment-store-proxy/enrolment-store/delegated-enrolments/:arn/group-id
    enrolment-store-proxy-->>-agent-client-enrolments: 200 OK (returns agent group ID)
    agent-client-enrolments->>+tax-enrolments: DELETE /tax-enrolments/groups/:agentGroupId/enrolments/:enrolmentKey
    tax-enrolments-->>-agent-client-enrolments: 204 No Content
    agent-client-enrolments->>+agent-client-relationships: DELETE /agent-client-relationships/agent/:arn/service/:service/client/:clientId
    agent-client-relationships-->>-agent-client-enrolments: 204 No Content
    agent-client-enrolments-->>-Upstream: 204 No Content
```
