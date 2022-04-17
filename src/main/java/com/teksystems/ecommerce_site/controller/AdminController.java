package com.teksystems.ecommerce_site.controller;


import com.teksystems.ecommerce_site.database.dao.ProductDAO;

import com.teksystems.ecommerce_site.database.entity.Product;

import com.teksystems.ecommerce_site.formbean.ProductFormBean;
import com.teksystems.ecommerce_site.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Controller
public class AdminController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/admin/productlisting", method = RequestMethod.GET)
    public ModelAndView adminProduct(@Valid ProductFormBean productFormBean) throws Exception {
        ModelAndView response = new ModelAndView();
        response.setViewName("admin/productListing");

        List<Product> allProducts = productDAO.findAll();
        response.addObject("allProducts", allProducts);

        return response;
    }

    @RequestMapping(value = "/admin/productlisting/productSubmit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView productSubmit(@Valid ProductFormBean productFormBean) throws Exception {
        ModelAndView response = new ModelAndView();
        response.setViewName("admin/productlisting");

        response.addObject("productFormBean", productFormBean);

        Product product = new Product();
        productService.getProductDetails( productFormBean, product);

        productDAO.save(product);

        return new ModelAndView("redirect:/admin/productlisting");
    }

//    @GetMapping("/admin/productlisting/edit/{productID}")
//    public ModelAndView productEdit(@PathVariable("productID") Integer productID) throws Exception {
//        ModelAndView response = new ModelAndView();
//        response.setViewName("admin/productlisting/edit/");
//
//        Product product = productDAO.findByProductID(productID);
//
//        ProductFormBean productFormBean = new ProductFormBean();
//
////        productService.getProductDetails( productFormBean, product);
//
//        productFormBean.setProductID(productID);
//        productFormBean.setProductName(product.getProductName());
//        productFormBean.setProductPrice(product.getProductPrice());
//        productFormBean.setProductStock(product.getProductStock());
//        productFormBean.setProductImage(product.getProductImage());
//        productFormBean.setProductThumbnail(product.getProductThumbnail());
//        productFormBean.setProductDescription(product.getProductDescription());
//        productFormBean.setProductCategory(product.getProductCategory());
//        productFormBean.setCreateDate(new Date());
//
//
//        // in this case we are adding the RegisterFormBean to the model
//        response.addObject("productFormBean", productFormBean);
//
//        return response;
//    }






//        @GetMapping("/admin/productEdit/{productID}")
//        public ModelAndView productEdit(@PathVariable("productID") Integer productID) throws Exception {



        @RequestMapping(value = "/admin/productEdit/{productID}", method = RequestMethod.GET)
        public ModelAndView productEdit(@RequestParam (value="productID", required = false) Integer productID) throws Exception {
        ModelAndView response = new ModelAndView();
        response.setViewName("admin/productEdit/");


        Product product = productDAO.findProductByProductID(productID);

        ProductFormBean productFormBean = new ProductFormBean();

//        productService.getProductDetails( productFormBean, product);

        productFormBean.setProductID(product.getProductID());
        productFormBean.setProductName(product.getProductName());
        productFormBean.setProductPrice(product.getProductPrice());
        productFormBean.setProductStock(product.getProductStock());
        productFormBean.setProductImage(product.getProductImage());
        productFormBean.setProductThumbnail(product.getProductThumbnail());
        productFormBean.setProductDescription(product.getProductDescription());
        productFormBean.setProductCategory(product.getProductCategory());
        productFormBean.setCreateDate(new Date());


        // in this case we are adding the RegisterFormBean to the model
        response.addObject("productFormBean", productFormBean);

        return response;
//          return new ModelAndView("redirect:/admin/productlisting");
    }

    @RequestMapping(value = "/admin/productlisting/delete{productID}", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView productDelete(@PathParam("productID") Integer productID) throws Exception {
        ModelAndView response= new ModelAndView();
        response.setViewName("admin/productlisting");

        Product product = productDAO.findProductByProductID( productID );
        if ( product == null ) {
            // this is an error
        } else {
            productDAO.delete(product);
        }






//        return response;
        return new ModelAndView("redirect:/admin/productlisting");
    }
}