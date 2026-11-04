package TaskFlowWorkflow {
  state def TaskState { state todo; state inProgress; state done; transition first todo then inProgress; transition first inProgress then done; transition first done then todo; }
}
