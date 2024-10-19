package murkeev.config;

import murkeev.dto.LoginVO;
import murkeev.dto.UserVO;
import murkeev.service.JwtUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Jwts;

import java.util.Date;


@RefreshScope
@Component
public class LoginFilter implements GatewayFilter {

    private Long JWT_ACCESS_VALIDITY = 60 * 60 * 1000L;
    private Long JWT_REFRESH_VALIDITY = 24 * 60 * 60 * 1000L;

    @Autowired
    private RouterValidator validator;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<LoginVO> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = UriComponentsBuilder.fromHttpUrl("user-service")
                .path("/api/v1/users/login")
                .toUriString();

        ResponseEntity<UserVO> response = WebClient.create(url)
                .get()
                .retrieve()
                .toEntity(UserVO.class)
                .block();
        UserVO userVO = response.getBody();

        String tokenId = RandomStringUtils.randomAlphanumeric(10);

        return new LoginVO(
                generate(userVO, tokenId, "ACCESS", JWT_ACCESS_VALIDITY),
                generate(userVO, tokenId, "REFRESH", JWT_REFRESH_VALIDITY)
        );

    }

    private String generate(UserVO userVO, String tokenId, String tokenType, long validity) {
        return Jwts.builder()
                .claims(Jwts.claims()
                        .subject(userVO.getId().toString())
                        .add("tokenId", tokenId)
                        .add("tokenType", tokenType)
                        .add("role", userVO.getRole())
                        .build()
                )
                .expiration(new Date(System.currentTimeMillis() + validity))
                .issuedAt(new Date())
                .compact();
    }

    private Mono<Void> onError(ServerWebExchange exchange, String s, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
