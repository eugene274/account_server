# zagar_io

## HIBERNATE
H2 in-memo db for tests 

@see accountservice.dao.accountservice.database
@see hibernate.cfg.xml
@see accountservice.dao

## RESPONSE FORMAT
Jackson API

@see accountservice.model.response
@see accountservice.model.mixin

## Authenticated users

accountservice.TokenService provides \<Token,Profile\> Map and some handy methods
accountservice.AccountService provides all accounting stuff (email, logout, etc)
