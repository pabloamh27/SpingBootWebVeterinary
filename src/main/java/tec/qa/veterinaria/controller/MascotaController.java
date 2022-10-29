package tec.qa.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tec.qa.veterinaria.interfaceServices.IClienteService;
import tec.qa.veterinaria.interfaceServices.IMascotaService;
import tec.qa.veterinaria.model.Cliente;
import tec.qa.veterinaria.model.Mascota;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping
public class MascotaController {

    @Autowired
    private IMascotaService mascotaService;

    @Autowired
    private IClienteService clienteService;


    @GetMapping("/verMascotas/{cedula_cliente}")
    public String listarMascotasCliente(@PathVariable int cedula_cliente, Model model){
        Optional<Cliente> cliente = clienteService.listarId(cedula_cliente);
        List<Mascota> mascotas = mascotaService.listarByCliente(cliente.get());
        model.addAttribute("mascotas",mascotas);
        return "mascota/viewCliente/indexMascota";
    }

    @GetMapping("/listarMascotas/{cedula_cliente}")
    public String listarMascotasMedico(@PathVariable int cedula_cliente, Model model){
        Optional<Cliente> cliente = clienteService.listarId(cedula_cliente);
        List<Mascota> mascotas = mascotaService.listarByCliente(cliente.get());
        model.addAttribute("mascotas",mascotas);
        return "mascota/viewMedico/indexMascota";
    }

    @GetMapping("/nuevaMascota")
    public String agregarMascota(Model model){
        model.addAttribute("mascota",new Mascota());
        return "mascota/viewMedico/formMascota";
    }

    @PostMapping("/guardarMascota")
    public String guardarMascota(@Valid Mascota mascota, Model model){
        mascotaService.save(mascota);
        return "redirect:/listarMascotas";
    }

    @GetMapping("/editarMascota/{id}")
    public String editarMascota(@PathVariable int id, Model model){
        Optional<Mascota> mascota = mascotaService.listarId(id);
        model.addAttribute("mascota",mascota);
        return "mascota/viewMedico/formMascota";
    }

    @GetMapping("/eliminarMascota/{id}")
    public String eliminarMascota(@PathVariable int id,Model model){
        mascotaService.delete(id);
        return "redirect:/listarMascotas";
    }

}