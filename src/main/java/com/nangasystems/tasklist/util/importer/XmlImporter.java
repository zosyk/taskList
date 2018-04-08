package com.nangasystems.tasklist.util.importer;

import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.dbo.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
public class XmlImporter implements Importer {

    @Override
    public ObservableList<Task> importTasks(File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Tasks tasks = (Tasks) unmarshaller.unmarshal(file);

        return FXCollections.observableArrayList(tasks.getTasks());
    }
}
