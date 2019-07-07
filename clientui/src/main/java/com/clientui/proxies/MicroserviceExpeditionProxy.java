package com.clientui.proxies;

import com.clientui.beans.ExpeditionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-expedition")
@RequestMapping("/microservice-expedition")
public interface MicroserviceExpeditionProxy {

    @GetMapping("Expeditions/command/{idCommande}")
    ExpeditionBean etatExpedition(@PathVariable("idCommande") int idCommande);
}
