ALTER TABLE tasks_tb
ADD COLUMN data_conclusao DATE;

COMMENT ON COLUMN tasks_tb.data_conclusao IS 'Data em que a tarefa foi concluída';