package com.eldorado.controller;


import com.eldorado.service.CustomersOrdersCalculationsService;
import com.eldorado.service.CustomersOrdersXMLParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.xml.stream.XMLStreamException;
import java.io.IOException;


@Controller
@Scope("session")
public class CustomersXMLUploadController {

    @Autowired
    @Qualifier("customersOrdersCalculationsService")
    private CustomersOrdersCalculationsService customersOrdersCalculationsService;

    @Autowired
    @Qualifier("customersOrdersXMLParserService")
    private CustomersOrdersXMLParserService customersOrdersXmlParserService;

    @RequestMapping("/index")
    public String home(Model model) {
        return "index";

    }

    @RequestMapping(value="/upload", method= RequestMethod.POST, produces = "text/html; charset=UTF-8")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException, XMLStreamException {
        if (!file.isEmpty()) {

            customersOrdersXmlParserService.parse(file.getInputStream());
            customersOrdersCalculationsService.setCustomers(customersOrdersXmlParserService.getParsedCustomers());


            model.addAttribute("Message", "Результаты:");
            model.addAttribute("TotalOrdersSum", customersOrdersCalculationsService.getTotalOrdersSum());
            model.addAttribute("MaxOrderSumCustomerName", customersOrdersCalculationsService.getMaxOrderSumCustomerName());
            model.addAttribute("MaxOrdersSum", customersOrdersCalculationsService.getMaxOrdersSum());
            model.addAttribute("MinOrdersSum", customersOrdersCalculationsService.getMinOrdersSum());
            model.addAttribute("TotalOrdersCount", customersOrdersCalculationsService.getTotalOrdersCount());
            model.addAttribute("AverageOrderSum", customersOrdersCalculationsService.getAverageOrderSum());


        } else {
            model.addAttribute("Message", "You failed to upload  because the file was empty.");
        }

        return "result";
    }

}