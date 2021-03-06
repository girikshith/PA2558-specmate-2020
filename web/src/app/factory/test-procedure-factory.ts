import { Config } from '../config/config';
import { IContainer } from '../model/IContainer';
import { TestProcedure } from '../model/TestProcedure';
import { TestStep } from '../model/TestStep';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { ElementFactoryBase } from './element-factory-base';
import { TestStepFactory } from './test-step-factory';

export class TestProcedureFactory extends ElementFactoryBase<TestProcedure> {
    public create(parent: IContainer, commit: boolean, compoundId?: string, name?: string): Promise<TestProcedure> {
        compoundId = compoundId || Id.uuid;
        let id = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let testProcedure: TestProcedure = new TestProcedure();
        testProcedure.name = name || Config.TESTPROCEDURE_NAME + ' ' + ElementFactoryBase.getDateStr();
        testProcedure.description = Config.TESTPROCEDURE_DESCRIPTION;
        testProcedure.id = id;
        testProcedure.url = url;
        testProcedure.recycled = false;
        testProcedure.hasRecycledChildren = false;
        testProcedure.isRegressionTest = false;

        return this.dataService.createElement(testProcedure, true, compoundId)
            .then(() => this.createTestStep(testProcedure, compoundId))
            .then(() => commit ? this.dataService.commit('create') : Promise.resolve())
            .then(() => testProcedure);
    }

    private createTestStep(testProcedure: TestProcedure, compoundId: string): Promise<TestStep> {
        let factory: TestStepFactory = new TestStepFactory(this.dataService);
        return factory.create(testProcedure, false, compoundId);
    }
}
