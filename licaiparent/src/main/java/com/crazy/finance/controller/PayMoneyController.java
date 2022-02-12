package com.crazy.finance.controller;

import com.crazy.finance.bean.PayMoney;
import com.crazy.finance.service.PayMoneyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class PayMoneyController {
    @Autowired
    private PayMoneyService payMoneyService;

    @GetMapping("/user/finance/toPayMoney.html")
    public String toPayMoney(Model model,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PayMoney> list = payMoneyService.selectAllPayMoney();
        PageInfo<PayMoney> pageInfo = new PageInfo<>(list);
        model.addAttribute("pmPageInfo",pageInfo);
        model.addAttribute("payMoneyList", list);

        model.addAttribute("pageTopBarInfo","工资理财界面");
        model.addAttribute("activeUrl1","financeActive");
        model.addAttribute("activeUrl2","payMoneyActive");
        return "user/finance/paymoney";
    }
}
