package cl.entel.eai.writer;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.XygoAddressDAO;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.XygoAddress;
import cl.entel.eai.pipeline.writer.DAOWriter;

import java.util.List;

public class XygoAddressDAOWriter extends DAOWriter<XygoAddressDAO, List<XygoAddress>> {

    public XygoAddressDAOWriter() { }

    @Override
    protected void init() { }

    @Override
    public Void process(List<XygoAddress> input) throws PipelineException {
        try {
            this.setOutputRecords(input.size());
            this.getConfiguration().getDao().createGeoXygoAddress(input);
        } catch (DAOException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_WRITER, e.getMessage());
        }

        return null;
    }
}
