package cl.entel.eai.runner;

import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

public abstract class DAOPipelineRunner<D, O> extends PipelineRunner<DAOConfiguration<D>, O>{

    DAOPipelineRunner() { }

    @Override
    public void run() throws PipelineException{
        this.init();

        long offset = this.getReader().getConfiguration().getOffset();
        long total = this.getReader().getConfiguration().getTotalRecords();

        while (offset < total){
            this.getReader().getConfiguration().computeChuckSize();
            long chunkSize = this.getReader().getConfiguration().getChunkSize();

            this.executePipeline();

            offset += chunkSize;
            this.getReader().getConfiguration().incrementOffset(chunkSize);
        }
    }

    private long computeChuckSize(long offset, long chunkSize, long total) {
        return (offset + chunkSize) >= total ? total - offset : chunkSize;
    }

    public abstract void executePipeline() throws PipelineException;
}
