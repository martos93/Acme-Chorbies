package controllers.administrator;

import controllers.AbstractController;
import domain.Banner;
import forms.BannerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.BannerService;

import javax.validation.Valid;

@Controller
@RequestMapping("/banner/administrator")
public class BannerAdministratorController extends AbstractController {
    //Services
    @Autowired
    private BannerService bannerService;

    //List
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        result = new ModelAndView("banner/list");
        Banner banner = new Banner();
        for(Banner b:bannerService.findAll()){
            banner = b;
            break;
        }

        result.addObject("banner", banner.getBanners());

        result.addObject("requestUri", "banner/administrator/list.do");
        return result;
    }

    //Add and Delete
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView result;
        BannerForm bannerForm = new BannerForm();

        result = new ModelAndView("banner/add");
        result.addObject("bannerForm", bannerForm);
        result.addObject("requestUri", "banner/administrator/add.do");

        return result;
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST, params = "save")
    public ModelAndView send(@Valid final BannerForm bannerForm, final BindingResult binding) {
        ModelAndView result;

        if (binding.hasErrors()) {
            result = new ModelAndView("banner/add");
            result.addObject("bannerForm", bannerForm);
            result.addObject("requestUri", "banner/administrator/add.do");
        }else{
            try{
                //Revisar este for
                bannerService.addBanner(bannerForm.getUrl());
                result = new ModelAndView("redirect:list.do");
            }catch(Throwable oops){
                result = new ModelAndView("banner/add");
                result.addObject("bannerForm", bannerForm);
                result.addObject("message", "banner.commit.error");
                result.addObject("requestUri", "banner/administrator/add.do");
            }
        }
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam final String bannerURL) {
        ModelAndView result;

        try{
            bannerService.deleteBanner(bannerURL);
            result = new ModelAndView("redirect:list.do");
        }catch(Throwable oops){
            result = new ModelAndView("redirect:list.do");
        }
        return result;
    }

}
