package com.crazy.finance.controller;

import com.crazy.finance.bean.Bank;
import com.crazy.finance.service.BankService;
import com.crazy.finance.utils.JsonUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BankController {
    @Autowired
    private BankService bankService;

    /**
     * 新增推荐银行
     *
     * @return
     */
    @PostMapping("/admin/addBank")
    @ResponseBody
    public JsonUtils addBank(Bank bank) {
        bankService.insertBank(bank);
        return JsonUtils.success();

    }

    /**
     * 更新时回显信息
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/getBankInfoById/{id}")
    @ResponseBody
    public JsonUtils getBankInfoById(@PathVariable("id") Integer id) {
        Bank bank = bankService.selectBankById(id);
        return JsonUtils.success().add("bank", bank);
    }

    /**
     * 更新
     *
     * @param id
     * @return
     */
    @PutMapping("/admin/updateBank/{id}")
    @ResponseBody
    public JsonUtils updateBank(@PathVariable("id") Integer id, Bank bank) {
        bank.setId(id);
        bankService.updateBank(bank);

        return JsonUtils.success();

    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/deleteBankById/{id}")
    @ResponseBody
    public JsonUtils deleteBankById(@PathVariable("id") Integer id) {
        bankService.deleteBankById(id);

        return JsonUtils.success();

    }

    /**
     * 银行推荐管理界面（管理员）
     *
     * @param model
     * @return
     */
    @GetMapping("/admin/finance/toBank.html")
    public String adminToBank(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                              Model model) {
        PageHelper.startPage(pageNum, pageSize);
        List<Bank> list = bankService.selectAllBank();
        PageInfo<Bank> pageInfo = new PageInfo<>(list);
        model.addAttribute("bankPageInfo", pageInfo);
        model.addAttribute("bankList", list);

        model.addAttribute("pageTopBarInfo", "银行推荐界面");
        model.addAttribute("activeUrl1", "financeActive");
        model.addAttribute("activeUrl2", "bankActive");
        return "admin/finance/bank";
    }

    /**
     * 银行推荐见界面（用户）
     *
     * @param model
     * @return
     */
    @GetMapping("/user/finance/toBank.html")
    public String userToBank(Model model) {
        List<Bank> list = bankService.selectAllBank();
        model.addAttribute("bankList", list);

        model.addAttribute("pageTopBarInfo", "银行推荐界面");
        model.addAttribute("activeUrl1", "financeActive");
        model.addAttribute("activeUrl2", "bankActive");
        return "user/finance/bank";
    }
}
