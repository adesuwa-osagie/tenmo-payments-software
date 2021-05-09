package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.Transfers;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransfersController {

    @Autowired
    private TransfersDAO transfersDAO;

    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfers> listAllTransfers() {
        return transfersDAO.listAllTransfers();
    }

    @RequestMapping(path = "/transfers/{userAccountId}", method = RequestMethod.GET)
    public List<Transfers> listAllTransfersForUser(@PathVariable ("userAccountId") Long currentUserAccountId){
        return transfersDAO.listAllTransfersForUser(currentUserAccountId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "accounts/{userId}/transfer/{receiverId}", method = RequestMethod.POST)
    public void addTransfer(@RequestParam @ApiParam("Valid values are Send and Request") String transferType, @RequestParam String transferStatus,
                            @PathVariable("userId") Long currentUserId,
                            @PathVariable("receiverId") Long receiverUserId,
                            @RequestParam BigDecimal amountToTransfer){
        transfersDAO.addTransfer(transferType, transferStatus, currentUserId, receiverUserId, amountToTransfer);
    }





}
