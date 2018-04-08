package com.nangasystems.tasklist.util.exporter;

import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.dbo.Tasks;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class XmlExporter implements Exporter {

    @Override
    public void export(List<Task> tasks, File exportFile) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(new Tasks(tasks), new FileWriter(exportFile));
    }
}
