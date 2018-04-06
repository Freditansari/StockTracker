import { BaseEntity } from './../../shared';

export class StockList implements BaseEntity {
    constructor(
        public id?: number,
        public symbol?: string,
        public name?: string,
        public lastSale?: string,
        public marketCap?: string,
        public ipoYear?: string,
        public sector?: string,
        public industry?: string,
        public adr?: string,
        public summaryQuote?: string,
        public portfolios?: BaseEntity[],
    ) {
    }
}
