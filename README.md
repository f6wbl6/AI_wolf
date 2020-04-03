# AI_wolf

registered in 2018.6.17

## Overview

人狼知能ハッカソン（2018年6月15〜17日開催）にて作成したエージェント.

（ハッカソン参考URL）：http://aiwolf.org/archives/1761

（結果参考URL）：http://aiwolf.org/archives/1916

## Description

基本的にはGAT2018の優勝者rsaitoさんのエージェントベース.（つまりcndlさんの孫）
 - 基本戦略は「過去の戦績から一番強いエージェントを優先的に排除する」
    - メタ的ではあるが，実際の人間の対戦においても「強い人は嘘をついてる」という考えは自然な発想.
 - 5人村ではルールベースでの戦略設計.
    - 投票先をどちらか選ばなければいけないような状況で，上述の基本戦略を取る
 - 15人村はオリジナルのまま.

## Others

エージェントパス：tera.aiwolf.TeraAgentPlayer

## Author

[f6wbl6](https://github.com/f6wbl6)
