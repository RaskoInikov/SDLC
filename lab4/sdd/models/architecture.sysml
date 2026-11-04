package TaskFlowArchitecture {
  part def Browser;
  part def WebUI;
  part def TaskController;
  part def TaskService;
  part def TaskRepository;
  part taskFlow { part browser: Browser; part ui: WebUI; part controller: TaskController; part service: TaskService; part repository: TaskRepository; }
}
