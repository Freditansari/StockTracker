import { BaseEntity } from './../../shared';

export class Portfolio implements BaseEntity {
    constructor(
        public id?: number,
        public lastPrice?: number,
        public purchasePrice?: number,
        public purchaseDate?: any,
        public gain?: number,
        public lastUpdated?: any,
        public userLogin?: string,
        public userId?: number,
        public stockListSymbol?: string,
        public stockListId?: number,
    ) {
    }
}
