package org.jruby.ir.instructions;

import org.jruby.ir.Operation;
import org.jruby.ir.operands.Label;
import org.jruby.ir.operands.Operand;
import org.jruby.ir.transformations.inlining.InlinerInfo;
import org.jruby.ir.targets.JVM;
import org.jruby.runtime.Block;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

public class JumpInstr extends Instr {
    public final Label target;

    public JumpInstr(Label target) {
        super(Operation.JUMP);
        this.target = target;
    }

    public Operand[] getOperands() {
        return EMPTY_OPERANDS;
    }

    @Override
    public String toString() {
        return super.toString() + " " + target;
    }

    public Label getJumpTarget() {
        return target;
    }

    @Override
    public Instr cloneForInlinedScope(InlinerInfo ii) {
        return new JumpInstr(ii.getRenamedLabel(target));
    }

    @Override
    public Instr cloneForBlockCloning(InlinerInfo ii) {
        return new JumpInstr(target);
    }

    public void compile(JVM jvm) {
        jvm.method().goTo(jvm.methodData().getLabel(target));
    }
}
