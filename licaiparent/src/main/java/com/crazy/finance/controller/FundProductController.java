package com.crazy.finance.controller;

import com.crazy.finance.bean.FundProduct;
import com.crazy.finance.service.FundProductService;
import com.crazy.finance.utils.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FundProductController {
    @Autowired
    private FundProductService fundProductService;

    /**
     * 新增基金理财产品
     *
     * @return
     */
    @PostMapping("/admin/addFundProduct")
    @ResponseBody
    public JsonUtils addFundProduct(FundProduct fundProduct) {
        fundProductService.insertFundProduct(fundProduct);

        return JsonUtils.success();

    }

    /**
     * 更新时回显信息
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/getFundProductInfoById/{id}")
    @ResponseBody
    public JsonUtils getFundProductInfoById(@PathVariable("id") Integer id) {
        FundProduct fundProduct = fundProductService.selectFundProductById(id);
        return JsonUtils.success().add("fundProduct", fundProduct);
    }

    /**
     * 更新
     *
     * @param id
     * @return
     */
    @PutMapping("/admin/updateFundProduct/{id}")
    @ResponseBody
    public JsonUtils updateFundProduct(@PathVariable("id") Integer id, FundProduct fundProduct) {
        fundProduct.setId(id);
        fundProductService.updateFundProduct(fundProduct);

        return JsonUtils.success();

    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/deleteFundProductById/{id}")
    @ResponseBody
    public JsonUtils deleteFundProductById(@PathVariable("id") Integer id) {
        fundProductService.deleteFundProductById(id);
        return JsonUtils.success();

    }

    /**
     * 期限理财产品界面（管理员）
     *
     * @param model
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/admin/finance/toFundProduct.html")
    public String adminToTermFinancial(Model model,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FundProduct> list = fundProductService.selectAllFundProduct();
        PageInfo<FundProduct> pageInfo = new PageInfo<>(list);
        model.addAttribute("fpPageInfo", pageInfo);
        model.addAttribute("fundProductList", list);

        model.addAttribute("pageTopBarInfo", "基金理财界面");
        model.addAttribute("activeUrl1", "financeActive");
        model.addAttribute("activeUrl2", "fundProductActive");

        return "admin/finance/fundproduct";
    }

    /**
     * 期限理财产品界面（用户）
     *
     * @param model
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/user/finance/toFundProduct.html")
    public String userToTermFinancial(Model model,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FundProduct> list = fundProductService.selectAllFundProduct();
        PageInfo<FundProduct> pageInfo = new PageInfo<>(list);
        model.addAttribute("fpPageInfo", pageInfo);
        model.addAttribute("fundProductList", list);

        model.addAttribute("pageTopBarInfo", "基金理财界面");
        model.addAttribute("activeUrl1", "financeActive");
        model.addAttribute("activeUrl2", "fundProductActive");

        return "user/finance/fundproduct";
    }
}
