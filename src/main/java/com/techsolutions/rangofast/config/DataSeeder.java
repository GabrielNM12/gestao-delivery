package com.techsolutions.rangofast.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.techsolutions.rangofast.model.Cliente;
import com.techsolutions.rangofast.model.Endereco;
import com.techsolutions.rangofast.model.Entregador;
import com.techsolutions.rangofast.model.ItemPedido;
import com.techsolutions.rangofast.model.Pedido;
import com.techsolutions.rangofast.model.Pessoa;
import com.techsolutions.rangofast.model.Restaurante;
import com.techsolutions.rangofast.model.enums.CategoriaRestaurante;
import com.techsolutions.rangofast.model.enums.StatusPedido;
import com.techsolutions.rangofast.repository.ClienteRepository;
import com.techsolutions.rangofast.repository.EntregadorRepository;
import com.techsolutions.rangofast.repository.PedidoRepository;
import com.techsolutions.rangofast.repository.RestauranteRepository;

/**
 * Popula o banco H2 com dados de exemplo na inicializacao.
 *
 * Demonstra o uso de COLLECTIONS e POLIMORFISMO: uma lista de
 * {@link Pessoa} contendo Clientes e Entregadores e percorrida de forma
 * uniforme, chamando metodos sobrescritos por cada subclasse.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final RestauranteRepository restauranteRepository;
    private final ClienteRepository clienteRepository;
    private final EntregadorRepository entregadorRepository;
    private final PedidoRepository pedidoRepository;

    public DataSeeder(RestauranteRepository restauranteRepository,
                      ClienteRepository clienteRepository,
                      EntregadorRepository entregadorRepository,
                      PedidoRepository pedidoRepository) {
        this.restauranteRepository = restauranteRepository;
        this.clienteRepository = clienteRepository;
        this.entregadorRepository = entregadorRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) {
        if (restauranteRepository.count() > 0) {
            return; // evita duplicar em reinicializacoes
        }

        // ----- Restaurantes -----
        Restaurante burguer = new Restaurante("Burguer do Ze",
                CategoriaRestaurante.LANCHES, new BigDecimal("6.90"),
                new Endereco("Av. Brasil", "100", "Centro", "Blumenau", "SC", "89000-000"));
        Restaurante pizza = new Restaurante("Pizzaria Bella",
                CategoriaRestaurante.PIZZARIA, new BigDecimal("9.50"),
                new Endereco("Rua das Flores", "250", "Garcia", "Blumenau", "SC", "89010-000"));
        Restaurante sushi = new Restaurante("Sushi House",
                CategoriaRestaurante.JAPONESA, new BigDecimal("12.00"),
                new Endereco("Rua XV", "500", "Velha", "Blumenau", "SC", "89020-000"));
        restauranteRepository.saveAll(Arrays.asList(burguer, pizza, sushi));

        // ----- Clientes e Entregadores (lista polimorfica de Pessoa) -----
        Cliente ana = new Cliente("Ana Souza", "ana@email.com", "47999990001",
                new Endereco("Rua A", "10", "Centro", "Blumenau", "SC", "89000-100"));
        Cliente bruno = new Cliente("Bruno Lima", "bruno@email.com", "47999990002");
        clienteRepository.saveAll(Arrays.asList(ana, bruno));

        Entregador carlos = new Entregador("Carlos Dias", "carlos@email.com",
                "47999990003", "Moto Honda CG");
        entregadorRepository.save(carlos);

        // POLIMORFISMO + COLLECTIONS: mesma operacao para tipos diferentes
        List<Pessoa> pessoas = new ArrayList<>(Arrays.asList(ana, bruno, carlos));
        for (Pessoa p : pessoas) {
            System.out.println(p.gerarNotificacao("bem-vindo(a) a RangoFast!"));
        }

        // ----- Pedido de exemplo (COMPOSICAO com itens) -----
        Pedido pedido = new Pedido(ana, burguer);
        pedido.adicionarItem(new ItemPedido("X-Burguer", 2, new BigDecimal("24.90")));
        pedido.adicionarItem(new ItemPedido("Batata frita", 1, new BigDecimal("14.00")));
        pedido.setEntregador(carlos);
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedidoRepository.save(pedido);

        System.out.println(">>> Dados de exemplo carregados no H2.");
    }
}
